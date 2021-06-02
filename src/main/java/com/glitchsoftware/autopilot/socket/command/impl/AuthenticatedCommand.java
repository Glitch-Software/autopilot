package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.config.impl.Auth;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthenticatedPacket;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
public class AuthenticatedCommand extends Command {

    public AuthenticatedCommand() {
        super("authenticated");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final AuthenticatedPacket authenticatedPacket = (AuthenticatedPacket) packet;

        if(AutoPilot.INSTANCE.getCurrentUser() == null) {
            AutoPilot.INSTANCE.setCurrentUser(authenticatedPacket.getUser());
            AutoPilot.INSTANCE.setProfitableItems(authenticatedPacket.getJsonElement().getAsJsonArray());
        }
//        final com.glitchsoftware.autopilot.socket.packet.impl.AuthenticatedPacket authenticatedPacket =
//                (com.glitchsoftware.autopilot.socket.packet.impl.AuthenticatedPacket) packet;
//
//        System.out.println(((AuthenticatedPacket) packet).getUser().getUsername());
//    //    AutoPilot.INSTANCE.getWebSocket().send(new AuthenticatedPacket(authenticatedPacket.getUser()));


    }
}
