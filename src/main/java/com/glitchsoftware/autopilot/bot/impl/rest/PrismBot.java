package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.google.gson.JsonObject;
import mmarquee.automation.controls.Application;
import mmarquee.automation.controls.ElementBuilder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.swing.*;

import static club.minnced.discord.webhook.IOUtil.JSON;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Prism")
@RestManifest("https://sockets.prismaio.com/tasks")
public class PrismBot extends ConnectionBot {

    @Override
    public void onLoad() {
    }

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        if(getSessionID().isEmpty() || getSessionID() == null) {
            JOptionPane.showMessageDialog(null, "No Session Prism Set", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            if(getAutomation().findPane("Prism") == null) {
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }
            final String link = String.format("https://www.%s/product/~/%s.html", site, sku);

            if(send(link)) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean send(String link) {
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("url", link);

            final Request request = new Request.Builder()
                    .header("Authorization", "Bearer: " + getSessionID())
                    .url(getApiUrl())
                    .post(RequestBody.create(JSON, jsonObject.toString()))
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                return response.code() == 200;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
