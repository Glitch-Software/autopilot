package com.glitchsoftware.autopilot.app.command;

import com.glitchsoftware.autopilot.app.command.impl.*;
import com.glitchsoftware.autopilot.app.command.impl.bot.BotConnectionCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.DisconnectBotCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.TestBotCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.UpdateBotCommand;
import com.glitchsoftware.autopilot.app.command.impl.items.NeedItemsPacket;
import com.glitchsoftware.autopilot.app.command.impl.settings.DeactivateCommand;
import com.glitchsoftware.autopilot.app.command.impl.settings.TestWebhookCommand;
import com.glitchsoftware.autopilot.app.command.impl.settings.UpdateDeleteTimeoutCommand;
import com.glitchsoftware.autopilot.app.command.impl.settings.UpdateRPCCommand;
import com.glitchsoftware.autopilot.app.command.impl.task.DeleteTaskCommand;
import com.glitchsoftware.autopilot.app.command.impl.task.NewTaskCommand;
import com.glitchsoftware.autopilot.app.command.impl.task.UpdateTaskCommand;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
@Getter
public class WebSocketCommandManger {
    private final List<Command> commands = new LinkedList<>();

    public WebSocketCommandManger() {
        commands.add(new AuthenticateCommand());
        commands.add(new InitializeCommand());
        commands.add(new BotConnectionCommand());
        commands.add(new UpdateBotCommand());
        commands.add(new TestBotCommand());
        commands.add(new TestWebhookCommand());
        commands.add(new NewTaskCommand());
        commands.add(new DeleteTaskCommand());
        commands.add(new NeedItemsPacket());
        commands.add(new UpdateTaskCommand());
        commands.add(new AuthedCommand());
        commands.add(new DeactivateCommand());
        commands.add(new DisconnectBotCommand());
        commands.add(new UpdateDeleteTimeoutCommand());
        commands.add(new CloseCommand());
        commands.add(new UpdateRPCCommand());
    }
}
