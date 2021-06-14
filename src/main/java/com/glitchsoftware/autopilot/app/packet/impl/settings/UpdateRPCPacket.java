package com.glitchsoftware.autopilot.app.packet.impl.settings;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
@PacketManifest("update_rpc")
@Getter
public class UpdateRPCPacket extends Packet {

    @SerializedName("rpc")
    private boolean rpc;

}
