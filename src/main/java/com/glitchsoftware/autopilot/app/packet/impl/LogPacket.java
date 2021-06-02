package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("log")
public class LogPacket extends Packet {

    @SerializedName("level")
    private int level;

    @SerializedName("message")
    private String message;

    @SerializedName("time_stamp")
    private String timestamp;

    public LogPacket(int level, String message, String timestamp) {
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
    }
}
