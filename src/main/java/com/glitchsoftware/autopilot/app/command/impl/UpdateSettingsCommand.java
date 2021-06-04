package com.glitchsoftware.autopilot.app.command.impl;

import club.minnced.discord.webhook.WebhookClient;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.UpdateSettingsPacket;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
public class UpdateSettingsCommand extends Command {

    public UpdateSettingsCommand() {
        super("update_settings");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateSettingsPacket updateSettingsPacket = (UpdateSettingsPacket) packet;

        AutoPilot.INSTANCE.getConfig()
                .getWebHooks()
                .setDiscordWebhook(updateSettingsPacket.getDiscordWebhook());

        AutoPilot.INSTANCE.setDiscordWebhook(WebhookClient.withUrl(AutoPilot.INSTANCE
                .getConfig()
                .getWebHooks().getDiscordWebhook()));
    }
}
