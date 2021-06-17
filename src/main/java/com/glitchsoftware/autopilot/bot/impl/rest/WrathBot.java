package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import mmarquee.automation.controls.*;
import okhttp3.Request;
import okhttp3.Response;

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
            final Request request = new Request.Builder()
                    .url(String.format(getApiUrl(), link))
                    .get()
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                if(response.code() == 200) {
                    final String alteredBody = response.body().string()
                            .replace("Task", "")
                            .replaceAll("created", "")
                            .replaceAll("successfully", "")
                            .replaceAll(" ", "");
                    getLogger().info("Successfully created (" + alteredBody.split(",").length + ") tasks");
                    return true;
                }
            }
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
