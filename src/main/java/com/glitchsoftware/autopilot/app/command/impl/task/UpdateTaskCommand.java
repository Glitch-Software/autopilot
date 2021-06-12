package com.glitchsoftware.autopilot.app.command.impl.task;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.task.UpdateTaskPacket;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.Logger;

/**
 * @author Brennan
 * @since 6/6/2021
 **/
public class UpdateTaskCommand extends Command {

    public UpdateTaskCommand() {
        super("update_task");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateTaskPacket updateTaskPacket = (UpdateTaskPacket) packet;
        final Task task = AutoPilot.INSTANCE.getTaskManager().getTask(updateTaskPacket.getId());

        if(task != null) {
            task.setActive(updateTaskPacket.isActive());

            Logger.logInfo("[Task Update] - Task Updated " + (task.isActive() ? "deactivated" : "activated"));
        } else {
            Logger.logError("[Task Update] - Task not found");
        }
    }
}
