package com.glitchsoftware.autopilot.socket.packet.serializer;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
public class Serializer {

    /**
     * Serializes a packet to send to our client
     * @param packet our outgoing packet
     * @return serialized packet
     */
    public static String serialize(Packet packet) {
        return AutoPilot.INSTANCE.getGSON().toJson(packet);
    }

    /**
     * Deserializes a incoming JSON to a packet
     * @param input packet JSON
     * @return deserialized JSON packet
     */
    public static Packet deserialize(JsonObject input) {
        return AutoPilot.INSTANCE.getGSON().fromJson(input, (Type) AutoPilot.INSTANCE.getSocketPacketManager()
                .getPacketClasses().get(input.get("packet_name").getAsString()));
    }

}