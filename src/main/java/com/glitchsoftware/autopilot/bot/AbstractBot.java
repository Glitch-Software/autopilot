package com.glitchsoftware.autopilot.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.util.logger.BotLogger;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.sikuli.script.Screen;

import java.io.File;
import java.net.URL;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * Our abstract class for our bots that allow us to have a file path for every bot.
 **/
@Getter
public abstract class AbstractBot implements Bot {
    private String name;

    private File file;

    /**
     * All our bots utilizes the OCR library
     */
    private final Screen screen = new Screen();

    private BotLogger logger;

    private String basePath;

    /**
     * Our base constructor that gets our {@link BotManifest} to get our name
     */
    public AbstractBot() {
        if(getClass().isAnnotationPresent(BotManifest.class)) {
            this.name = getClass().getAnnotation(BotManifest.class).value();

            try {
                this.basePath = AutoPilot.INSTANCE.getImagesFile().getAbsolutePath() + File.separator + name.toLowerCase() + File.separator;
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.logger = new BotLogger(name);
        }
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonObject toJSON() {
        final JsonObject jsonObject = new JsonObject();

        if(getFile() != null) {
            jsonObject.addProperty("file_path", getFile().getAbsolutePath());
        }

        return jsonObject;
    }

    @Override
    public void fromJSON(JsonObject jsonObject) {
        if(jsonObject.has("file_path"))
            setFile(new File(jsonObject.get("file_path").getAsString()));
    }
}
