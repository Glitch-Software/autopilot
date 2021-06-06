package com.glitchsoftware.autopilot.app.packet.impl.items;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@PacketManifest("profitable_items")
public class ProfitableItemsPacket extends Packet {

    @SerializedName("items")
    private JsonArray itemsArray;

    public ProfitableItemsPacket(JsonArray itemsArray) {
        this.itemsArray = itemsArray;
    }
}
