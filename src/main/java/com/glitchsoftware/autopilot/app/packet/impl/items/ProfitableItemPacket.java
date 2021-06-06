package com.glitchsoftware.autopilot.app.packet.impl.items;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@PacketManifest("profitable_item")
public class ProfitableItemPacket extends Packet {

    @SerializedName("name")
    private String name;

    @SerializedName("sku")
    private String sku;

    @SerializedName("stockx_link")
    private String stockX;

    @SerializedName("image")
    private String image;

    public ProfitableItemPacket(String name, String sku, String stockX, String image) {
        this.name = name;
        this.sku = sku;
        this.stockX = stockX;
        this.image = image;
    }
}
