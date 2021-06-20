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

    @SerializedName("version")
    private String version;

    @SerializedName("discord_webhook")
    private String discordWebhook;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("rpc")
    private boolean rpc;

    @SerializedName("delete_timeout")
    private long deleteTimeout;

    @SerializedName("bots")
    private JsonArray bots;

    @SerializedName("tasks")
    private JsonArray tasks;

    public InitializedPacket() {
    }

    public InitializedPacket(User user, String version, String discordWebhook, String keywords, boolean rpc, long deleteTimeout, JsonArray bots, JsonArray tasks) {
        this.user = user;
        this.version = version;
        this.discordWebhook = discordWebhook;
        this.keywords = keywords;
        this.rpc = rpc;
        this.deleteTimeout = deleteTimeout;
        this.bots = bots;
        this.tasks = tasks;
    }
}
