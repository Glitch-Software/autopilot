package com.glitchsoftware.autopilot.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Brennan
 * @since 7/2/2021
 **/
public class ClasspathUtils {
    public static Path extractResourcesToTempDirectory(String source, String prefix, ClassLoader loader) throws IOException {
        Path tempFile = createDeletableTempDirectory(prefix);
        extractResources(source, tempFile, loader);
        return tempFile;
    }

    public static Path extractResourcesToTempDirectory(String source, String prefix, Class<?> klass) throws IOException {
        Path tempFile = createDeletableTempDirectory(prefix);
        extractResources(source, tempFile, klass);
        return tempFile;
    }

    private static Path createDeletableTempDirectory(String prefix) throws IOException {
        Path tempFile = Files.createTempDirectory(prefix);
        deleteOnExit(tempFile);
        return tempFile;
    }

    public static void extractResources(String source, Path targetPath, Class<?> klass) throws IOException {
        URL resource = klass.getResource(source);
        Objects.requireNonNull(resource, String.format("Resource %s was not found in Class %s", source, klass));

        internalExtractResources(resource, targetPath);
    }

    public static void extractResources(String source, Path targetPath, ClassLoader loader) throws IOException {
        URL resource = loader.getResource(source);
        Objects.requireNonNull(resource, String.format("Resource %s was not found in ClassLoader %s", source, loader));

        internalExtractResources(resource, targetPath);
    }

    private static void internalExtractResources(URL resource, Path targetPath) throws IOException {
        try {
            visitUriAsPath(
                    resource.toURI(),
                    sourcePath -> Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Path newPath = targetPath.resolve(sourcePath.getParent().relativize(file).toString());
                            Files.copy(file, newPath);
                            deleteOnExit(newPath);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            Path newPath = targetPath.resolve(sourcePath.getParent().relativize(dir).toString());
                            Files.copy(dir, newPath);
                            deleteOnExit(newPath);
                            return FileVisitResult.CONTINUE;
                        }
                    })
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteOnExit(Path path) {
        path.toFile().deleteOnExit();
    }

    /**
     * Handles nested jar URIs, as well as Spring's broken ones. See {@link ClasspathUtils#fixSpringUri(URI)} and {@link ClasspathUtils#visitUriRecursionHelper(FileSystem, List, PathConsumer)}for details.
     */
    public static <E extends Throwable> void visitUriAsPath(URI uri, PathConsumer<E> visitor) throws E, IOException {
        List<URI> uris = prepURIs(uri);
        visitUriRecursionHelper(FileSystems.getDefault(), uris, visitor);
    }

    /**
     * Recursively opens a URI that may have multiple levels of jar file.
     */
    private static <E extends Throwable> void visitUriRecursionHelper(FileSystem fs, List<URI> uris, PathConsumer<E> visitor) throws E, IOException {
        URI uri = uris.get(0);

        Path path;
        if (fs.provider().getScheme().equals("jar")) {
            // Custom URI parsing here because of a JDK bug
            String spec = uri.getSchemeSpecificPart();
            int sep = spec.lastIndexOf("!/");
            if (sep == -1)
                throw new IllegalArgumentException("URI: "
                        + uri
                        + " does not contain path info ex. jar:file:/c:/foo.zip!/BAR");
            path = fs.getPath(spec.substring(sep + 1));
        } else {
            path = fs.provider().getPath(uri);
        }

        if (uris.size() <= 1) {
            visitor.accept(path);
        } else {
            try (FileSystem newFs = FileSystems.newFileSystem(path, (ClassLoader) null)) {
                visitUriRecursionHelper(newFs, uris.subList(1, uris.size()), visitor);
            }
        }
    }

    /**
     * "Unwraps" the URI and returns each level, from smallest to largest.
     */
    private static ArrayList<URI> prepURIs(URI uri) {
        uri = fixSpringUri(uri);
        ArrayList<URI> uris = new ArrayList<>();
        uris.add(uri);
        while (!uri.getScheme().equals("file")) {
            String end = uri.getRawSchemeSpecificPart();
            String newUri = end.substring(0, end.lastIndexOf("!/"));
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new IllegalStateException(String.format("Unexpectedly formed an invalid URI: \"%s\"", newUri), e);
            }
            uris.add(uri);
        }
        Collections.reverse(uris);
        return uris;
    }

    /**
     * Needed because ZipFileSystem doesn't like nested {@code jar:}-scheme URIs and because Spring doesn't format nested {@code jar:}-scheme URIs properly.
     * <p>
     * Spring doesn't put a {@code jar:} scheme in the URI for each jar level it navigates. This method adds prefixes based on the number of jar-uri separators ({@code :/}).
     * <p>
     * Some example paths that have to be specially handled:
     *
     * <ul>
     *     <li>Bad Spring path: {@code jar:file:/foo.jar!/bar.jar!/baz}</li>
     *     <li>Correct path: {@code jar:jar:file:/foo.jar!/bar.jar!/baz}</li>
     * </ul>
     */
    private static URI fixSpringUri(URI uri) {
        int jars = numMatches(uri.toString(), "jar:");
        int jarEnds = numMatches(uri.toString(), "!/");

        while (jars < jarEnds) {
            try {
                uri = new URI("jar:" + uri);
            } catch (URISyntaxException e) {
                throw new IllegalStateException(String.format("Unexpectedly formed an invalid URI: \"jar:%s\"", uri), e);
            }
            jars++;
        }

        return uri;
    }

    /**
     * Counts non-overlapping substrings in a string.
     *
     * @param s      The String to search in.
     * @param toFind The String to find.
     * @return The number of non-overlapping instances found
     */
    private static int numMatches(final String s, final String toFind) {
        int num = 0;
        int index = 0;
        while ((index = s.indexOf(toFind, index)) != -1) {
            index += toFind.length();
            num++;
        }
        return num;
    }

    /**
     * Consumes a path and may throw an exception.
     *
     * @param <E> The type of exception that my be thrown.
     */
    @FunctionalInterface
    public interface PathConsumer<E extends Throwable> {
        void accept(Path path) throws E;
    }
}
