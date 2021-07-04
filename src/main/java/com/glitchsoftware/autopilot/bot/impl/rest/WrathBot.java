package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import org.sikuli.script.App;
import org.sikuli.script.Match;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 6/15/2021
 **/
@BotManifest("Wrath")
@RestManifest("http://localhost:32441/qt?input=%s")
public class WrathBot extends RestBot {

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        try {
            final App wrathApp = launchBot();

            getLogger().info("Waiting....");

            final Match match = getScreen().exists(getBasePath() + "tasks.png");
            if(match == null) {
                getScreen().wait(getBasePath() + "taskbutton.png");
                getScreen().click(getBasePath() + "taskbutton.png");
            }
            getLogger().success("Found");

            getLogger().info("Sending SKU to API");
            if(send(String.format("https://www.%s/product/~/%s.html", site, sku))) {
                getLogger().success("Sent to API");

                getLogger().info("Searching for QT group.");
                getScreen().wait(getBasePath() + "group.png");
                getScreen().click(getBasePath() + "group.png");

                AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(wrathApp));
            } else {
                getLogger().error("Failed to send to API");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean send(String link) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(String.format(getApiUrl(), link)).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = in.readLine().replace("Task", "")
                        .replaceAll("created", "")
                        .replaceAll("successfully", "")
                        .replaceAll(" ", "");

                getLogger().info("Successfully created (" + line.split(",").length + ") tasks");
                connection.disconnect();
                return true;
            }

            connection.disconnect();
            return false;
        } catch (Exception e) {
            getLogger().error("Error trying to send to API: " + e.getMessage());
        }

        return false;
    }

    private class DeleteThread implements Runnable {
        private final App panel;

        public DeleteThread(App panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            try {
                final long deleteTimeout = 5;
                getLogger().info("Waiting for Delete Timeout (" + deleteTimeout + " minutes)");
                TimeUnit.SECONDS.sleep(deleteTimeout);

                panel.focus();

                getLogger().info("Deleting Tasks");
                getScreen().wait(getBasePath() + "deleteall.png");
                getScreen().click(getBasePath() + "deleteall.png");
                getScreen().wait(getBasePath() + "delete.png");

                getScreen().click(getBasePath() + "delete.png");

                getLogger().success("Deleted Tasks");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
