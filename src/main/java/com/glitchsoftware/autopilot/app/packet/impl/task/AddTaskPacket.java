package com.glitchsoftware.autopilot.app.packet.impl.task;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManifest;
import com.glitchsoftware.autopilot.task.Task;
import com.google.gson.annotations.SerializedName;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@PacketManifest("add_task")
public class AddTaskPacket extends Packet {

    @SerializedName("id")
    private String id;

    @SerializedName("sku")
    private String sku;

    @SerializedName("bots")
    private String[] bots;

    @SerializedName("active")
    private boolean active;

    public AddTaskPacket(String id, String sku, String[] bots, boolean active) {
        this.id = id;
        this.sku = sku;
        this.bots = bots;
        this.active = active;
    }

    public AddTaskPacket(Task task) {
        this(task.getId(), task.getSku(), task.getBots(), task.isActive());
    }
}
