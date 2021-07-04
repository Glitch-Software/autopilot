package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.util.SiteDetector;
import com.google.gson.JsonObject;
import okhttp3.Request;
import okhttp3.Response;
import org.python.antlr.ast.Str;
import org.sikuli.script.App;

import javax.rmi.CORBA.Tie;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 7/3/2021
 **/
@BotManifest("Ominous")
@RestManifest("http://localhost:2002/quicktask?options=%s")
public class OminousBot extends RestBot {

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        try {
            final App app = launchBot();

            getLogger().info("Waiting...");
            getScreen().wait(getBasePath() + "icon.png");
            getLogger().success("Found");

            getLogger().info("Sending to API.");
            if(send(SiteDetector.getName(site), sku)) {
                getScreen().wait(getBasePath() + "taskgroups.png");
                getLogger().success("Sent to API");

                new Thread(new DeleteThread(app)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean send(String site, String sku) {
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("module", "Footsites");
            jsonObject.addProperty("site", site);
            jsonObject.addProperty("sku", sku);
            jsonObject.addProperty("quantity", 5);

            final Request request = new Request.Builder()
                    .url(String.format(getApiUrl(), URLEncoder.encode(jsonObject.toString())))
                    .get()
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                return response.body().string().contains("Quick task creation opened");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private class DeleteThread implements Runnable {
        private final App app;

        public DeleteThread(App app) {
            this.app = app;
        }

        @Override
        public void run() {
            try {
                getLogger().info("Waiting for delete timeout");
                TimeUnit.SECONDS.sleep(6);

                app.focus();

                getScreen().wait(getBasePath() + "delete.png");
                getScreen().click(getBasePath() + "delete.png");

                getScreen().wait(getBasePath() + "deletegroup.png");
                getScreen().click(getBasePath() + "deletegroup.png");

                getLogger().success("Deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
