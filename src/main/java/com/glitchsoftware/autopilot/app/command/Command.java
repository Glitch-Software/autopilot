package com.glitchsoftware.autopilot.app.command;

import com.glitchsoftware.autopilot.app.packet.Packet;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public interface Command {

    void execute(Packet packet);

}
