package com.glitchsoftware.autopilot.util.logger;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.LogPacket;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
public class BotLogger {
    private final String prefix;

    public BotLogger(String prefix) {
        this.prefix = prefix;
    }

    public void error(String message) {
        Logger.logError(String.format("[%s] - %s", prefix, message));
    }

    public void success(String message) {
        Logger.logSuccess(String.format("[%s] - %s", prefix, message));
    }

    public void info(String message) {
        Logger.logInfo(String.format("[%s] - %s", prefix, message));
    }

    public void warning(String message) {
        Logger.logWarning(String.format("[%s] - %s", prefix, message));
    }
}
