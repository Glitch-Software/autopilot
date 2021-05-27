package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.TestTaskPacket;

/**
 * @author Brennan
 * @since 5/25/2021
 **/
public class TaskCommand implements Command {

    @Override
    public void execute(Packet packet) {
        if(packet instanceof TestTaskPacket) {
            System.out.println("?");
//            Kodai.runBot("glitchtest", "test");
        }
    }
}
