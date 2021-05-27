package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import mmarquee.automation.controls.Application;
import mmarquee.automation.controls.ElementBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.swing.*;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static club.minnced.discord.webhook.IOUtil.JSON;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Kylin")
@RestManifest("https://www.kylinbot.io/api/quicktask/create")
public class KylinBot extends ConnectionBot {

    @Getter
    private String license;

    @Override
    public void onLoad() {
        this.license = grabLicense();

        if(license != null)
            System.out.println("Grabbed Kylin License");
    }

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        if(getSessionID().isEmpty() || getSessionID() == null) {
            JOptionPane.showMessageDialog(null, "No Kylin Session Set", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(license == null) {
            JOptionPane.showMessageDialog(null, "Failed to grab license.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            if(getAutomation().findPane("Kylin") == null) {
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            for(int i = 0; i < taskQuantity; i++)
                send(String.format("https://www.%s/product/~/%s.html", site, sku), sku);

            AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    private String grabLicense() {
        try {
            final Request request = new Request.Builder()
                    .url("https://www.kylinbot.io/api/userInfo")
                    .header("Authorization", "Bear " + getSessionID())
                    .get()
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                if(response.code() == 200) {
                    final JsonObject object = JsonParser.parseString(response.body().string()).getAsJsonObject();

                    final JsonArray productsArray = object.getAsJsonObject("data").getAsJsonArray("products");

                    for(JsonElement jsonElement : productsArray) {
                        if(jsonElement instanceof JsonObject) {
                            final JsonObject productObject = (JsonObject) jsonElement;

                            if(productObject.get("tool_id").getAsInt() == 1)
                                return productObject.get("license_key").getAsString();
                        }
                    }
                }

                return null;
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    private void delete() {
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tool_id", 1);

            final Request request = new Request.Builder()
                    .header("Authorization", "Bear " + getSessionID())
                    .url("https://www.kylinbot.io/api/quicktask/delete")
                    .post(RequestBody.create(JSON, jsonObject.toString()))
                    .build();

            getOkHttpClient().newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean send(String link, String sku) {
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tool_id", 1);
            jsonObject.addProperty("license_key", getLicense());
            jsonObject.addProperty("data", String.format("input=%s&sku=%s", URLEncoder.encode(link), sku));

            final Request request = new Request.Builder()
                    .header("Authorization", "Bear " + getSessionID())
                    .url(getApiUrl())
                    .post(RequestBody.create(JSON, jsonObject.toString()))
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                if(response.code() == 200)
                    return response.body().string().contains("0");

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private class DeleteThread implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.MINUTES.sleep(6);

                delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
