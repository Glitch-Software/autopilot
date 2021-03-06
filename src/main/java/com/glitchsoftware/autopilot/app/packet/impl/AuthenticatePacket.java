package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("authenticate")
@Getter
public class AuthenticatePacket extends Packet {

    @SerializedName("license")
    private String license;

}
