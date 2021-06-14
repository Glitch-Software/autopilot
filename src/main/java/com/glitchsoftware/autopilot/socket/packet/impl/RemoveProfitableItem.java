package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
@PacketManifest("remove_profitableitem")
@Getter
public class RemoveProfitableItem extends Packet {

    @SerializedName("sku")
    private String sku;
}
