package com.glitchsoftware.autopilot.socket.packet;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/28/2021
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
        if(getClass().isAnnotationPresent(PacketManifest.class))
            this.name = getClass().getAnnotation(PacketManifest.class).value();
    }
}
