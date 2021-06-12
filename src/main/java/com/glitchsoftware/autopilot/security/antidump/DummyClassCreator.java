package com.glitchsoftware.autopilot.security.antidump;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
public class DummyClassCreator {

    private static InsnList dummyJavaCode() {
        final InsnList insnList = new InsnList();
        insnList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        insnList.add(new LdcInsnNode("Ur not cool."));
        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
        insnList.add(new TypeInsnNode(NEW, "java/lang/Throwable"));
        insnList.add(new InsnNode(DUP));
        insnList.add(new LdcInsnNode("pwned"));
        insnList.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Throwable", "<init>", "(Ljava/lang/String;)V", false));
        insnList.add(new InsnNode(ATHROW));

        return insnList;
    }

    public static byte[] createDummyClass(String name) {
        final ClassNode classNode = new ClassNode();
        classNode.name = name.replace('.', '/');
        classNode.access = ACC_PUBLIC;
        classNode.version = V1_8;
        classNode.superName = "java/lang/Object";
        final MethodNode methodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC, "<clinit>", "()V", null, null);
        methodNode.instructions = dummyJavaCode();
        classNode.methods.add(methodNode);

        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);

        return classWriter.toByteArray();
    }

}
