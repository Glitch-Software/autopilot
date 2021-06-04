package com.glitchsoftware.autopilot.app.command.impl.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.bot.TestBotPacket;
import com.glitchsoftware.autopilot.bot.Bot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;

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
            if(bot instanceof ConnectionBot) {
                ((ConnectionBot) bot).runBot("footlocker", "glitchtest", 1);
            } else {
                ((BasicBot) bot).runBot("footlocker", "glitchtest", "test", 1);
            }
        } else {

        }
    }
}
