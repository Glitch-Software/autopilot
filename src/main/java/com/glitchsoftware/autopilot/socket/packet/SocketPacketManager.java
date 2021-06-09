package com.glitchsoftware.autopilot.socket.packet;

import com.glitchsoftware.autopilot.socket.packet.impl.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@Getter
public class SocketPacketManager {
    /**
     * Our packets map
     *
     * key - packet name
     * value - packet class
     */
    private final Map<String, Class<?>> packetClasses = new HashMap<>();

    /**
     * Only add packets that get sent to our websocket
     */
    public SocketPacketManager() {
        packetClasses.put("handshake", HandshakePacket.class);
        packetClasses.put("ping", PingPacket.class);
        packetClasses.put("authenticated", AuthenticatedPacket.class);
        packetClasses.put("profitable_item", ProfitableItemPacket.class);
        packetClasses.put("error", ErrorPacket.class);
        packetClasses.put("successfully_deactivated", SuccessfullyDeactivatedPacket.class);
    }

}