package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.model.User;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.AuthenticatePacket;
import com.glitchsoftware.autopilot.app.packet.impl.SuccessfullyAuthenticatedPacket;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class AuthenticateCommand implements Command {

    @Override
    public void execute(Packet packet) {
        if(packet instanceof AuthenticatePacket) {
            final User user = new User("scrim", "scrim@glitchsoftware.com");
            AutoPilot.INSTANCE.getWebSocket().send(new SuccessfullyAuthenticatedPacket(user));
            System.out.println(((AuthenticatePacket) packet).getLicense());
        }
    }
}
