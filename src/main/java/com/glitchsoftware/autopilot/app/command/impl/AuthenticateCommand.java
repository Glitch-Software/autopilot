package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.AuthenticatePacket;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthPacket;
import com.glitchsoftware.autopilot.util.Utils;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class AuthenticateCommand extends Command {

    public AuthenticateCommand() {
        super("authenticate");
    }


    @Override
    public void execute(Packet packet) {
        AutoPilot.INSTANCE.getSocketConnection().send(new AuthPacket(((AuthenticatePacket) packet).getLicense(),
                Utils.getHWID()));
    }
}
