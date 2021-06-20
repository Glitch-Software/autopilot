package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import mmarquee.automation.controls.*;
import okhttp3.Request;
import okhttp3.Response;

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
            if(getAutomation().findPane("Wrath AIO") == null) {
                getLogger().info("Launching...");

                if(!getFile().exists()) {
                    getLogger().error("Failed to find file!");
                    return false;
                }

                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }
            getLogger().info("Waiting...");

            Panel wrathPanel = getAutomation().findPane("Wrath AIO");
            while (wrathPanel == null) {
                wrathPanel = getAutomation().findPane("Wrath AIO");
            }
            getLogger().success("Found");
            wrathPanel.getElement().setFocus();

            Hyperlink hyperlink = getHyper(wrathPanel);
            while (hyperlink == null) {
                hyperlink = getHyper(wrathPanel);
            }
            hyperlink.click();

            Thread.sleep(500);

            wrathPanel = getAutomation().findPane("Wrath AIO");

            Group group = wrathPanel.getGroup(0);

            wrathPanel = getAutomation().findPane("Wrath AIO");

            group.getChildren(true).get(0).invoke();

            getLogger().info("Sending SKU to API");
            if(send(String.format("https://www.%s/product/~/%s.html", site, sku))) {
                getLogger().success("Sent to API");
            } else {
                getLogger().error("Failed to send to API");
            }

            AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(wrathPanel));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Hyperlink getHyper(Panel panel) {
        try {
            return panel.getHyperlink(2);
        } catch (Exception  e) {

        }

        return null;
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
            e.printStackTrace();
        }

        return false;
    }

    private class DeleteThread implements Runnable {
        private final Panel panel;

        public DeleteThread(Panel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            try {
                final long deleteTimeout = AutoPilot.INSTANCE.getConfig().getDeleteTimeout();
                getLogger().info("Waiting for Delete Timeout (" + deleteTimeout + " minutes)");
                TimeUnit.MINUTES.sleep(deleteTimeout);

                panel.getElement().setFocus();

                getLogger().info("Stopping Tasks");
                panel.getButton("Stop all").invoke();

                getLogger().info("Deleting Tasks");
                panel.getButton("Delete all").invoke();

                Thread.sleep(500);

                getLogger().success("Deleted Tasks");
                panel.getButton("Delete").invoke();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
