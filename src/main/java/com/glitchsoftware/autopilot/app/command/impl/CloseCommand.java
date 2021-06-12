package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;

/**
 * @author Brennan
 * @since 6/9/2021
 **/
public class CloseCommand extends Command {

    public CloseCommand() {
        super("close");
    }

    @Override
    public void execute(Packet packet) {
        System.exit(-1);
    }
}
