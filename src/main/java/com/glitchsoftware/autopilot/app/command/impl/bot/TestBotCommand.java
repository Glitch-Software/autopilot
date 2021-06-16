package com.glitchsoftware.autopilot.app.command.impl.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.bot.TestBotPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.Logger;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
public class TestBotCommand extends Command {

    public TestBotCommand() {
        super("test_bot");
    }

    @Override
    public void execute(Packet packet) {
        final TestBotPacket testBotPacket = (TestBotPacket) packet;
        final Bot bot = AutoPilot.INSTANCE.getBotManager().getBots().get(testBotPacket.getId());

        if(bot != null) {
            Logger.logSuccess("[Bot Test] - " + bot.getName());

            AutoPilot.INSTANCE.getExecutorService().execute(() -> {
                if(bot instanceof RestBot) {
                    ((RestBot) bot).runBot("footlocker.com", "glitchtest", 1);
                } else {
                    ((BasicBot) bot).runBot("footlockerus", new Task("glitchtest", new String[]{bot.getName()},
                            1));
                }
            });
        } else {
            Logger.logError("[Bot Error] Failed to find Bot(" + testBotPacket.getId() + ")");
        }
    }
}
