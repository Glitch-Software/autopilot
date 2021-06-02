package com.glitchsoftware.autopilot.app.packet.impl;


import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.glitchsoftware.autopilot.socket.model.User;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("authenticated")
public class AuthenticatedPacket extends Packet {

    @SerializedName("user")
    private User user;

    public AuthenticatedPacket(User user) {
        this.user = user;
    }
}
