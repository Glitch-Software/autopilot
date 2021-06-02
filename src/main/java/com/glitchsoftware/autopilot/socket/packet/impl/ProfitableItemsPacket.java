package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@PacketManifest("profitable_items")
@Getter
public class ProfitableItemsPacket extends Packet {

    @SerializedName("items")
    private JsonElement itemsElement;

}
