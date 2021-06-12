package com.glitchsoftware.autopilot.security;

import com.glitchsoftware.autopilot.security.antidump.DummyClassCreator;
import com.glitchsoftware.autopilot.security.antidump.StructDissasembler;
import com.glitchsoftware.autopilot.security.check.CheckManager;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class Security {

    public static void start() {
        setPackageNameFilter();
        disableJavaAgents();
        StructDissasembler.disassembleStruct();

        new CheckManager();
    }

    private static void disableJavaAgents() {
        try {
            final byte[] bytes = DummyClassCreator.createDummyClass("sun/instrument/InstrumentationImpl");
            getUnsafe()
                    .defineClass("sun.instrument.InstrumentationImpl", bytes, 0, bytes.length, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void setPackageNameFilter() {
        try {
            final byte[] bytes = DummyClassCreator.createDummyClass("com/glitchsoftware/security/antidump/MaliciousClassFilter");
            getUnsafe().defineClass("com.glitchsoftware.security.antidump.MaliciousClassFilter", bytes, 0, bytes.length, null, null);
            System.setProperty("sun.jvm.hotspot.tools.jcore.filter", "com.glitchsoftware.security.antidump.MaliciousClassFilter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Unsafe getUnsafe() {
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
