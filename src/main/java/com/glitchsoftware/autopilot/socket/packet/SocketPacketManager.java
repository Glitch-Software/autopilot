package com.glitchsoftware.autopilot.socket.packet;

import com.glitchsoftware.autopilot.socket.packet.impl.AuthenticatedPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.HandshakePacket;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.ProfitableItemsPacket;
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
        packetClasses.put("profitable_items", ProfitableItemsPacket.class);

//        packetClasses.put("handshake", HandshakePacket.class);
//        packetClasses.put("authenticate", AuthPacket.class);
    }

}