package com.glitchsoftware.autopilot.app.packet.impl.task;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@PacketManifest("delete_task")
@Getter
public class DeleteTaskPacket extends Packet {

    @SerializedName("id")
    private String id;

}
