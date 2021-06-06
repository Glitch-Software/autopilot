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

    @SerializedName("bots")
    private JsonArray bots;

    @SerializedName("tasks")
    private JsonArray tasks;

    public InitializedPacket() {
    }

    public InitializedPacket(User user, JsonArray bots, JsonArray tasks) {
        this.user = user;
        this.bots = bots;
        this.tasks = tasks;
    }
}
