package com.glitchsoftware.autopilot.app.command.impl.settings;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.settings.UpdateDeleteTimeoutPacket;
import com.glitchsoftware.autopilot.util.Logger;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
public class UpdateDeleteTimeoutCommand extends Command {

    public UpdateDeleteTimeoutCommand() {
        super("update_delete_timeout");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateDeleteTimeoutPacket deleteTimeoutPacket = (UpdateDeleteTimeoutPacket) packet;

        System.out.println(deleteTimeoutPacket.getTimeout());
        AutoPilot.INSTANCE.getConfig().setDeleteTimeout(deleteTimeoutPacket.getTimeout());

        AutoPilot.INSTANCE.getConfig().save();

        Logger.logInfo("[Settings Update] - Updated Delete Timeout");
    }
}
