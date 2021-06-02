package com.glitchsoftware.autopilot.util;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.packet.impl.PingPacket;

import java.awt.*;
import java.time.Instant;

/**
 * @author Brennan
 * @since 5/29/2021
 **/
public class Embeds {

    public static void sendTaskStarted(PingPacket.PingProduct product) {
        final WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();

        embedBuilder.setColor(new Color(0x30E29B).getRGB());
        embedBuilder.setTimestamp(Instant.now());

        embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("Monitor found " + product.getSku(), ""));
        embedBuilder.setThumbnailUrl(product.getImage());
        embedBuilder.setDescription(String.format("[%s](%s)", product.getName(), product.getUrl()));

        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "Price", product.getPrice()));
        embedBuilder.addField(new WebhookEmbed.EmbedField(false, "SKU", product.getSku()));

        final WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("Glitch: AutoPilot");
        webhookMessageBuilder.setAvatarUrl("https://cdn.discordapp.com/attachments/700223049466904641/838133373208625152/logo.png");
        webhookMessageBuilder.addEmbeds(embedBuilder.build());

        WebhookClient
                .withUrl("https://discord.com/api/webhooks/848035698685771786/1DjxbfHRzsx8mwi94zJV0S0CNm9kdZBpdD03CdMK0yc86KFm5kNncZBG-qiqQ3g6HIGy")
                .send(webhookMessageBuilder.build());
    }
}
