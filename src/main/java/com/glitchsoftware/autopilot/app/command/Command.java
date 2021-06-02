package com.glitchsoftware.autopilot.app.command;


import com.glitchsoftware.autopilot.app.packet.Packet;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
@Getter
public abstract class Command {

    private String packetName;

    public Command(String packetName) {
        this.packetName = packetName;
    }

    public abstract void execute(Packet packet);

}