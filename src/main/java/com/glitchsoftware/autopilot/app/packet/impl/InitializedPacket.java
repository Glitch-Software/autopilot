package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.glitchsoftware.autopilot.socket.model.User;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

/**
 * @author Brennan
 * @since 6/1/2021
 **/
@PacketManifest("initialized")
public class InitializedPacket extends Packet {

    @SerializedName("user")
    private User user;

    @SerializedName("items")
    private JsonElement jsonElement;

    public InitializedPacket() {
    }

    public InitializedPacket(User user, JsonElement jsonElement) {
        this.user = user;
        this.jsonElement = jsonElement;
    }

    public InitializedPacket(User user) {
        this.user = user;
    }
}
