package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.MessagePacket;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.ErrorPacket;

import javax.swing.*;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
public class ErrorCommand extends Command {

    public ErrorCommand() {
        super("error");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final ErrorPacket errorPacket = (ErrorPacket) packet;

        if(errorPacket.getType().equalsIgnoreCase("authentication") && errorPacket.getMessage().equalsIgnoreCase("Banned for ever")) {
            JOptionPane.showMessageDialog(null, "Sorry! You have been banned");

            System.exit(-1);
            return;
        }

        AutoPilot.INSTANCE.getWebSocket().send(new MessagePacket("error", errorPacket.getType(), errorPacket.getMessage()));
    }
}
