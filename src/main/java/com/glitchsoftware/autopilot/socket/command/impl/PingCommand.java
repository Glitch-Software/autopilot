package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.PublicSuccessPacket;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.Embeds;
import com.glitchsoftware.autopilot.util.logger.Logger;
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

        for(Task task : AutoPilot.INSTANCE.getTaskManager().getTasks()) {
            if(task.getSku().equalsIgnoreCase(pingPacket.getSku())
                    && task.isActive()
                    && !task.isRunning()) {
                final String site = SiteDetector.getURL(pingPacket.getSite());
                final String formattedURL = String.format("https://%s/~/%s.html", site, pingPacket.getSku());
                Logger.logSuccess("[SKU IN-STOCK] - " + task.getSku());
                for(String botName : task.getBots()) {
                    final Bot bot = AutoPilot.INSTANCE.getBotManager().getBotByName(botName);

                    if(bot != null) {
                        Embeds.sendTaskStarted(pingPacket.getProduct(), pingPacket.getSku(), pingPacket.getSite(), formattedURL, task.getBots());

                        Logger.logSuccess("[Task Started] - " + bot.getName() + " - " + task.getSku());

                        AutoPilot.INSTANCE.getSocketConnection().send(new PublicSuccessPacket(botName,
                                pingPacket.getSku(), pingPacket.getProduct().getName(), pingPacket.getProduct().getImage(),
                                formattedURL));

                        AutoPilot.INSTANCE.getExecutorService().execute(() -> {
                            if(bot instanceof RestBot) {
                                ((RestBot) bot).runBot(site, task.getSku(), task.getTaskQuantity());
                            } else {

                                String siteGroup = "footlockerus";

                                switch (site) {
                                    case "footlocker.com":
                                        siteGroup = "footlockerus";
                                        break;
                                    case "footlocker.ca":
                                        siteGroup = "footlockerca";
                                        break;
                                    case "kidsfootlocker.com":
                                        siteGroup = "kidsfootlocker";
                                        break;
                                    case "footaction.com":
                                        siteGroup = "footaction";
                                        break;
                                    case "champssports.com":
                                        siteGroup = "champssports";
                                        break;
                                    case "eastbay.com":
                                        siteGroup = "eastbay";
                                        break;
                                }
                                task.setRunning(true);
                                ((BasicBot) bot).runBot(siteGroup, task);
                            }
                        });
                    }
                }
            }
        }
    }
}
