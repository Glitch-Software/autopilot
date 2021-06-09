package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.SuccessfullyDeactivatedPacket;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
public class SuccessfullyDeactivatedCommand extends Command {

    public SuccessfullyDeactivatedCommand() {
        super("successfully_deactivated");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        AutoPilot.INSTANCE.getConfig().getAuth().setLicense("");
        AutoPilot.INSTANCE.getConfig().save();
        AutoPilot.INSTANCE.getWebSocket().setAuthed(false);
        AutoPilot.INSTANCE.setCurrentUser(null);
        AutoPilot.INSTANCE.setProfitableItems(null);

        AutoPilot.INSTANCE.getWebSocket().send(new SuccessfullyDeactivatedPacket());
    }
}
