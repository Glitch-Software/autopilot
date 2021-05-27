package com.glitchsoftware.autopilot.app.packet.serializer;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * Our serializer used to make serializing and deserializing packets easy for JSON
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
    public static Packet deserialize(String input) {
        final JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();

        return AutoPilot.INSTANCE.getGSON().fromJson(jsonObject, (Type) AutoPilot.INSTANCE.getPacketManager()
                .getPacketClasses().get(jsonObject.get("packet_name").getAsString()));
    }

}
