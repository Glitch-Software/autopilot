package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Brennan
 * @since 5/29/2021
 **/
@PacketManifest("ping")
@Getter
@NoArgsConstructor
public class PingPacket extends Packet {

    @SerializedName("site")
    private String site;

    @SerializedName("sku")
    private String sku;

    @SerializedName("product")
    private PingProduct product;

    @SerializedName("time_stamp")
    private long timestamp;

    @Getter
    public static class PingProduct {
        private String name, image, sku, price, url;
    }
}
