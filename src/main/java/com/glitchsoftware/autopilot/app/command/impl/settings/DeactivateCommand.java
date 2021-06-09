package com.glitchsoftware.autopilot.app.command.impl.settings;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.DeactivatePacket;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
public class DeactivateCommand extends Command {

    public DeactivateCommand() {
        super("deactivate");
    }

    @Override
    public void execute(Packet packet) {
        AutoPilot.INSTANCE.getSocketConnection().send(new DeactivatePacket());
    }
}
