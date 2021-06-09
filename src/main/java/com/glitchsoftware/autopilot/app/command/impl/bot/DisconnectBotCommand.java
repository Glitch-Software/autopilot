package com.glitchsoftware.autopilot.app.command.impl.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.bot.DisconnectBotPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.util.Logger;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
public class DisconnectBotCommand extends Command {

    public DisconnectBotCommand() {
        super("disconnect_bot");
    }

    @Override
    public void execute(Packet packet) {
        final DisconnectBotPacket disconnectBotPacket = (DisconnectBotPacket) packet;
        final Bot bot = AutoPilot.INSTANCE.getBotManager().getBots().get(disconnectBotPacket.getId());

        if(bot != null) {
            AutoPilot.INSTANCE.getBotManager().deleteBot(bot);

            Logger.logInfo("[Bot Removed] - " + bot.getName());
        } else {
            Logger.logError("[Bot Error] Failed to find Bot(" + disconnectBotPacket.getId() + ")");
        }
    }
}
