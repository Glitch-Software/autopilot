package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.model.User;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("authenticated")
@Getter
public class AuthenticatedPacket extends Packet {

    @SerializedName("user")
    private User user;

    @SerializedName("items")
    private JsonElement jsonElement;
}
