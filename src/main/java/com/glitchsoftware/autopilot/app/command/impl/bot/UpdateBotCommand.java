package com.glitchsoftware.autopilot.app.command.impl.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.bot.UpdateBotPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.util.logger.Logger;

import java.io.File;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
public class UpdateBotCommand extends Command {

    public UpdateBotCommand() {
        super("update_bot");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateBotPacket updateBotPacket = (UpdateBotPacket) packet;
        final Bot bot = AutoPilot.INSTANCE.getBotManager().getBots().get(updateBotPacket.getId());

        if(bot != null) {
            bot.setFile(new File(updateBotPacket.getFilePath()));

            AutoPilot.INSTANCE.getBotManager().saveBot(bot);
            Logger.logInfo("[Bot Updated] - " + bot.getName());
        } else {
            Logger.logError("[Bot Error] Failed to find Bot(" + updateBotPacket.getId() + ")");
        }
    }
}
