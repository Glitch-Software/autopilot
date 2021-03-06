package com.glitchsoftware.autopilot.config;

import club.minnced.discord.webhook.WebhookClient;
import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.config.impl.Auth;
import com.glitchsoftware.autopilot.config.impl.WebHooks;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Brennan
 * @since 6/1/2021
 **/
@Getter
@Setter
public class Config {

    @SerializedName("web_hooks")
    private WebHooks webHooks = new WebHooks();

    @SerializedName("auth")
    private Auth auth = new Auth();

    @SerializedName("discord_rich_presence")
    private boolean discordRPC = false;

    @SerializedName("delete_timeout")
    private int deleteTimeout = 6;

    @SerializedName("selected_language")
    private String selectedLanguage = "en";

    @SerializedName("keywords")
    private String keywords = "";

    public void save() {
        try {
            final File file = new File(AutoPilot.INSTANCE.getBaseFile(), "config.json");

            if(file.exists()) {
                file.delete();
                file.createNewFile();
            }

            final FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(AutoPilot.INSTANCE.getGSON().toJson(this));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            final File file = new File(AutoPilot.INSTANCE.getBaseFile(), "config.json");

            if(file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                final JsonObject jsonObject = AutoPilot.INSTANCE.getGSON().fromJson(bufferedReader.readLine(), JsonObject.class);

                this.setDeleteTimeout(jsonObject.get("delete_timeout").getAsInt());
                this.setDiscordRPC(jsonObject.get("discord_rich_presence").getAsBoolean());
                this.setSelectedLanguage(jsonObject.get("selected_language").getAsString());
                this.getWebHooks().setDiscordWebhook(jsonObject.getAsJsonObject("web_hooks").get("discord_webhook").getAsString());
                this.getAuth().setLicense(jsonObject.getAsJsonObject("auth").get("license").getAsString());
                this.setKeywords(jsonObject.get("keywords").getAsString());

                if(!this.getWebHooks().getDiscordWebhook().isEmpty()) {
                    AutoPilot.INSTANCE.setDiscordWebhook(WebhookClient.withUrl(this.getWebHooks().getDiscordWebhook()));
                }

                bufferedReader.close();
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
