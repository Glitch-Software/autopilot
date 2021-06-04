package com.glitchsoftware.autopilot.app.packet.impl;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
@PacketManifest("update_settings")
@Getter
public class UpdateSettingsPacket extends Packet {

    @SerializedName("discord_webhook")
    private String discordWebhook;
}
