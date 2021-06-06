package com.glitchsoftware.autopilot.app.packet.impl.task;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/6/2021
 **/
@PacketManifest("update_task")
@Getter
public class UpdateTaskPacket extends Packet {

    @SerializedName("id")
    private String id;

    @SerializedName("active")
    private boolean active;
}
