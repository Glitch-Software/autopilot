package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.AuthenticatePacket;
import com.glitchsoftware.autopilot.app.packet.impl.InitializedPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthPacket;

/**
 * @author Brennan
 * @since 6/1/2021
 **/
public class InitializeCommand extends Command {

    public InitializeCommand() {
        super("initialize");
    }

    @Override
    public void execute(Packet packet) {
        if(AutoPilot.INSTANCE.getCurrentUser() != null) {
            System.out.println("sending user info");
            AutoPilot.INSTANCE.getWebSocket().send(new InitializedPacket(AutoPilot.INSTANCE.getCurrentUser(),
                    AutoPilot.INSTANCE.getProfitableItems()));
        } else {
            AutoPilot.INSTANCE.getWebSocket().send(new InitializedPacket());
            //AutoPilot.INSTANCE.getSocketConnection().send(new AuthPacket(((AuthenticatePacket) packet).getLicense(), "test"));
        }
    }
}
