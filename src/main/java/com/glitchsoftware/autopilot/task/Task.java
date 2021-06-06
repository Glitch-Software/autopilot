package com.glitchsoftware.autopilot.task;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.Bot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
@Getter
@Setter
public class Task {

    private String id, sku;
    private String[] bots;
    private int taskQuantity;

    public Task() {
    }

    public Task(String sku, String[] bots, int taskQuantity) {
        this.id = UUID.randomUUID().toString();
        this.sku = sku;
        this.bots = bots;
        this.taskQuantity = taskQuantity;
    }

    public JsonObject toJSON() {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", getId());
        jsonObject.addProperty("sku", getSku());

        final JsonArray jsonArray = new JsonArray();

        for(String bot : bots) {
            jsonArray.add(bot);
        }
        jsonObject.add("bots", jsonArray);

        return jsonObject;
    }

    public void fromJSON(JsonObject jsonObject) {
        if(jsonObject.has("id"))
            setId(jsonObject.get("id").getAsString());

        if(jsonObject.has("sku"))
            setSku(jsonObject.get("sku").toString());

        if(jsonObject.has("bots")) {
            final JsonArray jsonArray = jsonObject.getAsJsonArray("bots");
            final String[] bots = new String[jsonArray.size()];

            for(int i = 0; i < jsonArray.size(); i++) {
                bots[i] =  jsonArray.get(i).getAsString();
            }

            setBots(bots);
        }
    }

}
