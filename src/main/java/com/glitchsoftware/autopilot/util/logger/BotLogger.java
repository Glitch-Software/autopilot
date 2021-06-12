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
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(0, String.format("[%s] - %s", prefix, message), timestamp()));
    }

    public void success(String message) {
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(1, String.format("[%s] - %s", prefix, message), timestamp()));
    }

    public void info(String message) {
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(2, String.format("[%s] - %s", prefix, message), timestamp()));
    }

    public void warning(String message) {
        AutoPilot.INSTANCE.getWebSocket().send(new LogPacket(3, String.format("[%s] - %s", prefix, message), timestamp()));
    }

    private String timestamp() {
        return new SimpleDateFormat("yyyy-dd-mm HH:mm:ss").format(new Date());
    }
}
