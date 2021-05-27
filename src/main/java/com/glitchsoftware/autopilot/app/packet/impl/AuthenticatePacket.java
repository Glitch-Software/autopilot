package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * This packet is sent to us when a user has successfully sent a license for us to authenticate
 **/
@Getter
@PacketManifest("authenticate")
public class AuthenticatePacket extends Packet {

    @SerializedName("license")
    private String license;

}
