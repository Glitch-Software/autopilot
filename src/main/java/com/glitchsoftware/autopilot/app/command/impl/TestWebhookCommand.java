package com.glitchsoftware.autopilot.app.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.TestWebhookPacket;
import com.glitchsoftware.autopilot.util.Embeds;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
public class TestWebhookCommand extends Command {

    public TestWebhookCommand() {
        super("test_webhook");
    }

    @Override
    public void execute(Packet packet) {
        final TestWebhookPacket testWebhookPacket = (TestWebhookPacket) packet;

        switch (testWebhookPacket.getType()) {
            case 1:
                if(!AutoPilot.INSTANCE.getConfig()
                        .getWebHooks()
                        .getDiscordWebhook()
                        .isEmpty()) {
                    final String webHook = AutoPilot.INSTANCE.getConfig().getWebHooks().getDiscordWebhook();

                    if(webHook.contains("discord")
                            &&
                            webHook.contains("webhooks")) {
                        Embeds.sendTestWebhook();
                    }
                }
                break;
            case 2:
                //#TODO slack
                break;
        }
    }
}
