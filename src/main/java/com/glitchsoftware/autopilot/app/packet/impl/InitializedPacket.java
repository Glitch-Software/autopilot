package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.glitchsoftware.autopilot.socket.model.User;
import com.google.gson.JsonArray;
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
    private JsonArray jsonElement;

    @SerializedName("bots")
    private JsonArray bots;

    public InitializedPacket() {
    }

    public InitializedPacket(User user, JsonArray jsonElement, JsonArray bots) {
        this.user = user;
        this.jsonElement = jsonElement;
        this.bots = bots;
    }

    public InitializedPacket(User user) {
        this.user = user;
    }
}
