package com.glitchsoftware.autopilot.app.packet.impl.settings;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/19/2021
 **/
@PacketManifest("update_keywords")
@Getter
public class UpdateKeywordsPacket extends Packet {

    @SerializedName("keywords")
    private String keywords;


}
