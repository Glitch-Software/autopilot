package com.glitchsoftware.autopilot.app.command.impl.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.bot.BotConnectionPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.util.BotConnection;
import com.glitchsoftware.autopilot.util.Logger;

import java.io.File;

/**
 * @author Brennan
 * @since 6/3/2021
 **/
public class BotConnectionCommand extends Command {

    public BotConnectionCommand() {
        super("bot_connection");
    }

    @Override
    public void execute(Packet packet) {
        final BotConnectionPacket connectionPacket = (BotConnectionPacket) packet;
        final Bot bot = AutoPilot.INSTANCE.getBotManager().getBots().get(connectionPacket.getId());

        if(bot != null) {
            if(bot instanceof ConnectionBot) {
                //# TODO add bot connection stuff again
                System.out.println(bot.getName());
                new BotConnection(bot.getName(), ((ConnectionBot) bot));
            }

            bot.setFile(new File(connectionPacket.getFilePath()));

            AutoPilot.INSTANCE.getBotManager().saveBot(bot);

            Logger.logInfo("[Bot Add] - " + bot.getName());
        } else {
            Logger.logError("[Bot Error] Failed to find Bot(" + connectionPacket.getId() + ")");
        }
    }
}
