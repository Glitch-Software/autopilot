package com.glitchsoftware.autopilot.app.packet.impl.items;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
@PacketManifest("remove_item")
@AllArgsConstructor
public class RemoveItemPacket extends Packet {

    @SerializedName("sku")
    private String sku;

}
