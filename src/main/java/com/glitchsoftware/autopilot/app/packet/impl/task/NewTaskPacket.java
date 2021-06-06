package com.glitchsoftware.autopilot.app.packet.impl.task;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@PacketManifest("new_task")
@Getter
public class NewTaskPacket extends Packet {

    @SerializedName("sku")
    private String sku;

    @SerializedName("task_quantity")
    private int taskQuantity;

    @SerializedName("bots")
    private int[] bots;

}
