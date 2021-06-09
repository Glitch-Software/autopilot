package com.glitchsoftware.autopilot.app.packet.impl.bot;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
@PacketManifest("disconnect_bot")
@Getter
public class DisconnectBotPacket extends Packet {

    @SerializedName("bot_id")
    private int id;

}
