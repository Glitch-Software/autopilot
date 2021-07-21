package com.glitchsoftware.autopilot.bot;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.impl.basic.KodaiBot;
import com.glitchsoftware.autopilot.bot.impl.basic.WhatBotBot;
import com.glitchsoftware.autopilot.bot.impl.rest.*;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.SiteDetector;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Brennan
 * @since 6/2/2021
 **/
@Getter
public class BotManager {
    private final List<Bot> bots = new LinkedList<>();

    public BotManager() {
        this.bots.add(new KodaiBot());
        this.bots.add(new WhatBotBot());
    //    this.bots.add(new CybersoleBot());
        this.bots.add(new KylinBot());
    //    this.bots.add(new PrismBot());
        this.bots.add(new WrathBot());
        this.bots.add(new OminousBot());

        loadBots();
    }

    public JsonArray getBotsAsJSON() {
        final JsonArray jsonArray = new JsonArray();

        for(int i = 0; i < getBots().size(); i++) {
            final Bot bot = getBots().get(i);

            final JsonObject jsonObject = bot.toJSON();
            jsonObject.addProperty("name", bot.getName());
            jsonObject.addProperty("bot_id", i);
            jsonObject.addProperty("active", bot.getFile() != null);
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    public void loadBots() {
        try {
            getBots().forEach(bot -> {
                final File file = new File(AutoPilot.INSTANCE.getBaseFile(), "bots" + File.separator + bot.getName() + ".json");

                if(file.exists()) {
                    try {
                        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                        final JsonObject jsonObject = JsonParser.parseString(bufferedReader.readLine()).getAsJsonObject();
                        bot.fromJSON(jsonObject);

                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            getBots()
                    .stream()
                    .filter(bot -> bot instanceof ConnectionBot)
                    .filter(bot -> bot.getFile() != null)
                    .forEach(bot -> ((ConnectionBot) bot).onLoad());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBot(Bot bot) {
        try {
            final File file = new File(AutoPilot.INSTANCE.getBotsFile(), bot.getName() + ".json");
            if(file.exists()) {
                file.delete();
                bot.setFile(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBot(Bot bot) {
        try {
            final File file = new File(AutoPilot.INSTANCE.getBotsFile(), bot.getName() + ".json");
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
            final FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(AutoPilot.INSTANCE.getGSON().toJson(bot.toJSON()));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bot getBotByName(String name) {
        for(Bot bot : getBots()) {
            if(bot.getName().equalsIgnoreCase(name))
                return bot;
        }

        return null;
    }

    public void executeBot(Bot bot, String site, Task task) {
        AutoPilot.INSTANCE.getExecutorService().execute(() -> {
            if(bot instanceof RestBot) {
                ((RestBot) bot).runBot(site, task.getSku(), task.getTaskQuantity());
            } else {
                ((BasicBot) bot).runBot(site, task);
            }
        });
    }
}
