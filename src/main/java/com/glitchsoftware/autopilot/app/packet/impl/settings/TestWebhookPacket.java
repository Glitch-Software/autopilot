package com.glitchsoftware.autopilot.app.packet.impl.settings;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
@PacketManifest("test_webhook")
@Getter
public class TestWebhookPacket extends Packet {

    @SerializedName("type")
    private int type;

    @SerializedName("webhook_url")
    private String webhookUrl;

}
