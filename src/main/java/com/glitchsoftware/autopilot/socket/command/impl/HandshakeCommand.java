package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthPacket;
import com.google.common.io.BaseEncoding;


/**
 * @author Brennan
 * @since 5/28/2021
 **/
public class HandshakeCommand extends Command {

    public HandshakeCommand() {
        super("handshake");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        System.out.println("handshake");

        if(!AutoPilot.INSTANCE.getConfig().getAuth().getLicense().isEmpty())
            client.send(new AuthPacket(AutoPilot.INSTANCE.getConfig().getAuth().getLicense(), "test"));
    }
}
