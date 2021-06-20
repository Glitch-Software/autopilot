package com.glitchsoftware.autopilot.app.command.impl.task;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.task.AddTaskPacket;
import com.glitchsoftware.autopilot.app.packet.impl.task.NewTaskPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class NewTaskCommand extends Command {

    public NewTaskCommand() {
        super("new_task");
    }

    @Override
    public void execute(Packet packet) {
        final NewTaskPacket newTaskPacket = (NewTaskPacket) packet;
        final String sku = newTaskPacket.getSku();

        final int taskQuantity = newTaskPacket.getTaskQuantity();

        final List<String> bots = new LinkedList<>();
        for(int botID : newTaskPacket.getBots()) {
            final Bot bot = AutoPilot.INSTANCE.getBotManager().getBots().get(botID);

            if(bot != null)
                bots.add(bot.getName());
        }

        if (sku.contains(",")) {
            for(String product : sku.split(",")) {
                final Task task = new Task(product, bots.toArray(new String[bots.size()]), taskQuantity);
                AutoPilot.INSTANCE.getTaskManager().addTask(task);
                AutoPilot.INSTANCE.getWebSocket().send(new AddTaskPacket(task));
            }

            Logger.logInfo("[SKU MASS ADD]- " + sku);
        } else if(sku.equalsIgnoreCase("keywords")) {
            final Task task = new Task("keywords", bots.toArray(new String[bots.size()]), taskQuantity);

            AutoPilot.INSTANCE.getTaskManager().addTask(task);
            AutoPilot.INSTANCE.getWebSocket().send(new AddTaskPacket(task));

            Logger.logInfo("[SKU ADD] - using keywords");

        } else {
            final Task task = new Task(sku, bots.toArray(new String[bots.size()]), taskQuantity);
            AutoPilot.INSTANCE.getTaskManager().addTask(task);
            AutoPilot.INSTANCE.getWebSocket().send(new AddTaskPacket(task));

            Logger.logInfo("[SKU ADD] - " + task.getSku());
        }

    }
}
