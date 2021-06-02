package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;

/**
 * @author Brennan
 * @since 5/29/2021
 **/
public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final PingPacket pingPacket = (PingPacket) packet;
        System.out.println(pingPacket.getSite());
        System.out.println(pingPacket.getSku());

        System.out.println(pingPacket.getProduct().getName());
    }
}
