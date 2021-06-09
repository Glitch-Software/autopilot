package com.glitchsoftware.autopilot.app.packet.impl.settings;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
@PacketManifest("update_delete_timeout")
@Getter
public class UpdateDeleteTimeoutPacket extends Packet {

    @SerializedName("delete_timeout")
    private int timeout;
}
