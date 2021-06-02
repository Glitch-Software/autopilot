package com.glitchsoftware.autopilot.util;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.LogPacket;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
public class Logger {

    private static String timestamp() {
        return new SimpleDateFormat("[HH:mm:ss]").format(new Date());
    }

    public static void logError(String message) {
        message = Ansi.colorize(message, Attribute.RED_TEXT());
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(0, message, timestamp()));
        System.out.println(timestamp() + " [ERROR] " + message);
    }

    public static void logSuccess(String message) {
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(1, message, timestamp()));

        message = Ansi.colorize(message, Attribute.GREEN_TEXT());
        System.out.println(timestamp() + " [SUCCESS] " + message);
    }

    public static void logInfo(String message) {
        message = Ansi.colorize(message, Attribute.BLUE_TEXT());
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(2, message, timestamp()));
        System.out.println(timestamp() +  " [INFO] " + message);
    }

    public static void logWarning(String message) {
        message = Ansi.colorize(message, Attribute.YELLOW_TEXT());
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(3, message, timestamp()));
        System.out.println(timestamp() +  " [WARNING] " + message);
    }
}
