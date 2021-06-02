package com.glitchsoftware.autopilot.app.packet;

import com.glitchsoftware.autopilot.app.packet.impl.AuthenticatePacket;
import com.glitchsoftware.autopilot.app.packet.impl.InitializePacket;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * Our PacketManger is used to identify incoming packets used for {@link com.glitchsoftware.autopilot.app.packet.serializer.Serializer}
 **/
@Getter
public class WebSocketPacketManager {
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
    public WebSocketPacketManager() {
        packetClasses.put("initialize", InitializePacket.class);
        packetClasses.put("authenticate", AuthenticatePacket.class);
//        packetClasses.put("log", LogPacket.class);
//        packetClasses.put("authenticate", AuthenticatePacket.class);
//        packetClasses.put("test_packet", TestTaskPacket.class);
    }

}
