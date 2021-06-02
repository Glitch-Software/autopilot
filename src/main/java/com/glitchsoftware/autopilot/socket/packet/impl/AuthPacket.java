package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("authenticate")
@Getter
public class AuthPacket extends Packet {

    @SerializedName("license_key")
    private String licenseKey;

    @SerializedName("hardware_id")
    private String hardwareID;

    public AuthPacket(String licenseKey, String hardwareID) {
        this.licenseKey = licenseKey;
        this.hardwareID = hardwareID;
    }
}
