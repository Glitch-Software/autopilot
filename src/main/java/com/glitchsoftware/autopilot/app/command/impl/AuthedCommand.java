package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;

/**
 * @author Brennan
 * @since 6/6/2021
 **/
public class AuthedCommand extends Command {

    public AuthedCommand() {
        super("authed");
    }

    @Override
    public void execute(Packet packet) {
        AutoPilot.INSTANCE.getWebSocket().setAuthed(true);
    }
}
