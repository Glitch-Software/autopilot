package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.Embeds;
import com.glitchsoftware.autopilot.util.Logger;
import com.glitchsoftware.autopilot.util.SiteDetector;

/**
 * @author Brennan
 * @since 5/29/2021
 **/
public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final PingPacket pingPacket = (PingPacket) packet;

        System.out.println(pingPacket.getProduct().getName());

        for(Task task : AutoPilot.INSTANCE.getTaskManager().getTasks()) {
            if(task.getSku().contains(pingPacket.getSku()) && task.isActive()) {
                Logger.logSuccess("[SKU IN-STOCK] - " + task.getSku());
                //Embeds.sendTaskStarted(pingPacket.getProduct());
                for(String botName : task.getBots()) {
                    final Bot bot = AutoPilot.INSTANCE.getBotManager().getBotByName(botName);

                    if(bot != null) {
                        Logger.logSuccess("[Task Started] - " + bot.getName() + " - " + task.getSku());
                        if(bot instanceof RestBot) {

                        } else {
//                            ((BasicBot) bot).runBot(SiteDetector.getURL(pingPacket.getSite()), task.getSku(), "",
//                                    task.getTaskQuantity());
                        }
                    }
                }
            }
        }
    }
}
