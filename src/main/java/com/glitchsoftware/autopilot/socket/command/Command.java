package com.glitchsoftware.autopilot.socket.command;

import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@Getter
public abstract class Command {

    private String packetName;

    public Command(String packetName) {
        this.packetName = packetName;
    }

    public abstract void execute(Packet packet, SocketConnection client);

}