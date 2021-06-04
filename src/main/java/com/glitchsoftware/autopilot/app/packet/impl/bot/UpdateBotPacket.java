package com.glitchsoftware.autopilot.app.packet.impl.bot;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
@PacketManifest("update_bot")
@Getter
public class UpdateBotPacket extends Packet {

    @SerializedName("bot_id")
    private int id;

    @SerializedName("file_path")
    private String filePath;

}
