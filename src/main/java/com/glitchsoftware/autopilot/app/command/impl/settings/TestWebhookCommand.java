package com.glitchsoftware.autopilot.app.command.impl.settings;

import club.minnced.discord.webhook.WebhookClient;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.settings.TestWebhookPacket;
import com.glitchsoftware.autopilot.util.Embeds;
import com.glitchsoftware.autopilot.util.logger.Logger;

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
                AutoPilot.INSTANCE.getConfig()
                        .getWebHooks()
                        .setDiscordWebhook(testWebhookPacket.getWebhookUrl());

                AutoPilot.INSTANCE.getConfig().save();

                AutoPilot.INSTANCE.setDiscordWebhook(WebhookClient.withUrl(AutoPilot.INSTANCE
                        .getConfig()
                        .getWebHooks().getDiscordWebhook()));

                if(testWebhookPacket.getWebhookUrl().contains("discord") && testWebhookPacket.getWebhookUrl().contains("webhooks")) {
                    Embeds.sendTestWebhook();

                    Logger.logSuccess("[Discord Webhook] Sent Test");
                }
                break;
            case 2:
                //#TODO slack
                break;
        }
    }
}
