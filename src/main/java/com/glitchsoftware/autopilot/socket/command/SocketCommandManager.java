package com.glitchsoftware.autopilot.socket.command;

import com.glitchsoftware.autopilot.socket.command.impl.*;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@Getter
public class SocketCommandManager {
    private final List<Command> commands = new LinkedList<>();

    public SocketCommandManager() {
        commands.add(new HandshakeCommand());
        commands.add(new PingCommand());
        commands.add(new AuthenticatedCommand());
        commands.add(new ProfitableItemCommand());
        commands.add(new ErrorCommand());
        commands.add(new SuccessfullyDeactivatedCommand());
    }
}

