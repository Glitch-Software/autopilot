package com.glitchsoftware.autopilot.app.packet;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * Our packet class for incoming and outgoing packets
 **/
@Getter
public class Packet {

    /**
     * Our name used for serialization {@link com.glitchsoftware.autopilot.app.packet.serializer.Serializer}
     */
    @SerializedName("packet_name")
    private String name;

    /**
     * Packet constructor
     */
    public Packet() {
        if(getClass().isAnnotationPresent(PacketManifest.class)) {
            this.name = getClass().getAnnotation(PacketManifest.class).value();
        }
    }
}
