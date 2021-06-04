package com.glitchsoftware.autopilot.app.command;

import com.glitchsoftware.autopilot.app.command.impl.AuthenticateCommand;
import com.glitchsoftware.autopilot.app.command.impl.TestWebhookCommand;
import com.glitchsoftware.autopilot.app.command.impl.UpdateSettingsCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.BotConnectionCommand;
import com.glitchsoftware.autopilot.app.command.impl.InitializeCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.TestBotCommand;
import com.glitchsoftware.autopilot.app.command.impl.bot.UpdateBotCommand;
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
        commands.add(new UpdateSettingsCommand());
        commands.add(new TestWebhookCommand());
    }
}
