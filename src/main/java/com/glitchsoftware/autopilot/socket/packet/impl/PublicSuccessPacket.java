package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/10/2021
 **/
@PacketManifest("public_success")
@Getter
public class PublicSuccessPacket extends Packet {

    @SerializedName("bot_name")
    private String botName;

    @SerializedName("sku")
    private String sku;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("product_image")
    private String productImage;

    @SerializedName("product_link")
    private String productLink;

    public PublicSuccessPacket(String botName, String sku, String productName, String productImage, String productLink) {
        this.botName = botName;
        this.sku = sku;
        this.productName = productName;
        this.productImage = productImage;
        this.productLink = productLink;
    }
}

