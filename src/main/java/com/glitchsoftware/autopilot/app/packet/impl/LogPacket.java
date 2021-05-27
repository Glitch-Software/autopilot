package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * This packet is used for when we log something for our react-app to post
 **/
@PacketManifest("log")
public class LogPacket extends Packet {
    @SerializedName("level")
    private int level;

    @SerializedName("message")
    private String message;

    public LogPacket(int level, String message) {
        this.level = level;
        this.message = message;
    }
}
