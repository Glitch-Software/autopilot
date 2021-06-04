package com.glitchsoftware.autopilot.app.packet;

import com.glitchsoftware.autopilot.app.packet.impl.AuthenticatePacket;
import com.glitchsoftware.autopilot.app.packet.impl.TestWebhookPacket;
import com.glitchsoftware.autopilot.app.packet.impl.UpdateSettingsPacket;
import com.glitchsoftware.autopilot.app.packet.impl.bot.BotConnectionPacket;
import com.glitchsoftware.autopilot.app.packet.impl.InitializePacket;
import com.glitchsoftware.autopilot.app.packet.impl.bot.TestBotPacket;
import com.glitchsoftware.autopilot.app.packet.impl.bot.UpdateBotPacket;
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
        packetClasses.put("bot_connection", BotConnectionPacket.class);
        packetClasses.put("update_bot", UpdateBotPacket.class);
        packetClasses.put("test_bot", TestBotPacket.class);
        packetClasses.put("update_settings", UpdateSettingsPacket.class);
        packetClasses.put("test_webhook", TestWebhookPacket.class);
    }

}
