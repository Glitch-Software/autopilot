package com.glitchsoftware.autopilot.app.command.impl.task;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.task.DeleteTaskPacket;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.Logger;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class DeleteTaskCommand extends Command {

    public DeleteTaskCommand() {
        super("delete_task");
    }

    @Override
    public void execute(Packet packet) {
        final DeleteTaskPacket deleteTaskPacket = (DeleteTaskPacket) packet;

        final Task task = AutoPilot.INSTANCE.getTaskManager().getTask(deleteTaskPacket.getId());

        if(task != null) {
            AutoPilot.INSTANCE.getTaskManager().removeTask(task);

            Logger.logInfo("[Task Deleted] - Task Removed");
        } else {
            Logger.logError("[Task Error] - Task not found");
        }
    }
}
