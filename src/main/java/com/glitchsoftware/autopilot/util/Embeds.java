package com.glitchsoftware.autopilot.util;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author Brennan
 * @since 5/29/2021
 **/
public class Embeds {

    public static void sendTestWebhook() {
        final WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();

        embedBuilder.setColor(new Color(0x30E29B).getRGB());
        embedBuilder.setThumbnailUrl("https://cdn.discordapp.com/attachments/700223049466904641/838133373208625152/logo.png");
        embedBuilder.setTimestamp(Instant.now());

        embedBuilder.setDescription("This is a test.");

        final WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("Glitch: AutoPilot");
        webhookMessageBuilder.setAvatarUrl("https://cdn.discordapp.com/attachments/700223049466904641/838133373208625152/logo.png");
        webhookMessageBuilder.addEmbeds(embedBuilder.build());

        AutoPilot.INSTANCE.getDiscordWebhook().send(webhookMessageBuilder.build());
    }

    public static void sendTaskStarted(PingPacket.PingProduct product, String sku, String siteName, String url, String[] bots) {
        if(AutoPilot.INSTANCE.getConfig().getWebHooks().getDiscordWebhook().isEmpty()) {
            return;
        }
        final WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();

        embedBuilder.setColor(new Color(0x30E29B).getRGB());
        embedBuilder.setTimestamp(Instant.now());

        embedBuilder.setTitle(new WebhookEmbed.EmbedTitle(String.format("[%s] Starting Task", siteName), ""));
        embedBuilder.setThumbnailUrl(new String(Base64.getDecoder().decode(product.getImage())));
        embedBuilder.setDescription(String.format("[%s](%s)", product.getName(), url));

        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "SKU", sku));

        final StringBuilder botBuilder = new StringBuilder();

        botBuilder.append("```");
        for(String bot : bots) {
            botBuilder.append(bot);

            if(Arrays.asList(bots).indexOf(bot) < bots.length - 1)
                botBuilder.append(",");
        }
        botBuilder.append("```");

        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "Bots", botBuilder.toString()));

        final WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("Glitch: AutoPilot");
        webhookMessageBuilder.setAvatarUrl("https://cdn.discordapp.com/attachments/700223049466904641/838133373208625152/logo.png");
        webhookMessageBuilder.addEmbeds(embedBuilder.build());

        AutoPilot.INSTANCE.getDiscordWebhook().send(webhookMessageBuilder.build());
    }
}
