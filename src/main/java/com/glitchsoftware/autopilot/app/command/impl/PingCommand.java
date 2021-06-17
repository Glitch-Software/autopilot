package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.PongPacket;

import java.util.Date;

/**
 * @author Brennan
 * @since 6/16/2021
 **/
public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void execute(Packet packet) {
        AutoPilot.INSTANCE.getWebSocket().send(new PongPacket());
        AutoPilot.INSTANCE.setLastPing(new Date());
    }
}
