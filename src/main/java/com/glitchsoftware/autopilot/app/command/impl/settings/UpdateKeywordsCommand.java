package com.glitchsoftware.autopilot.app.command.impl.settings;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.settings.UpdateKeywordsPacket;
import com.glitchsoftware.autopilot.util.logger.Logger;

/**
 * @author Brennan
 * @since 6/19/2021
 **/
public class UpdateKeywordsCommand extends Command {

    public UpdateKeywordsCommand() {
        super("update_keywords");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateKeywordsPacket keywordsPacket = (UpdateKeywordsPacket) packet;

        AutoPilot.INSTANCE.getConfig().setKeywords(keywordsPacket.getKeywords());

        AutoPilot.INSTANCE.getConfig().save();

        Logger.logInfo("[Settings Update] - Updated Keywords");
    }
}
