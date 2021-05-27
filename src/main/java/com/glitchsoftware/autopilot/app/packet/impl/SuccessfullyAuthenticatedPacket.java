package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.model.User;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/25/2021
 *
 * This packet is sent to our client when they have been successfully authenticated
 **/
@PacketManifest("successfully_authenticated")
@Getter
public class SuccessfullyAuthenticatedPacket extends Packet {

    @SerializedName("user")
    private final User user;

    public SuccessfullyAuthenticatedPacket(User user) {
        this.user = user;
    }
}
