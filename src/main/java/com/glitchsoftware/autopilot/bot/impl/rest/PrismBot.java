package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.util.logger.Logger;
import com.google.gson.JsonObject;
import mmarquee.automation.controls.*;
import mmarquee.automation.controls.Button;
import mmarquee.automation.controls.Panel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

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
        if(getSessionID() == null) {
            getLogger().error("No Session ID set");
            return false;
        }

        try {
            if(getAutomation().findPane("PrismAIO") == null) {
                getLogger().info("Launching....");

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
            Panel prismPanel = getAutomation().findPane("PrismAIO");
            while (prismPanel == null) {
                prismPanel = getAutomation().findPane("PrismAIO");
                Thread.sleep(500);
            }

            getLogger().success("Found");

            getLogger().info("Loading...");
            Hyperlink homeButton = null;
            while (homeButton == null) {
                prismPanel = getAutomation().findPane("PrismAIO");
                homeButton = getHyper(prismPanel);
            }
            getLogger().success("Loaded");

            final String link = String.format("https://www.%s/product/~/%s.html", site, sku);

            getLogger().info("Sending SKU to API");
            if(send(link)) {
                getLogger().success("Sent to API");
                prismPanel.getElement().setFocus();

                Thread.sleep(500);

                getLogger().info("Finding table");
                AutomationBase table = null;
                for(AutomationBase automationBase : prismPanel.getChildren(true)) {
                    if(automationBase.getAriaRole().equalsIgnoreCase("grid")) {
                        table = automationBase;
                        getLogger().success("Found table");
                    }
                }
                Thread.sleep(500);

                prismPanel.getButton("Stop All").click();

                AutomationBase firstRow = table.getChildren(true).get(0);
                firstRow.invoke();

                if(taskQuantity > 1) {
                    final Robot robot = new Robot();
                    robot.setAutoDelay(10);

                    getLogger().info("Duplicating tasks (" + taskQuantity + ")");
                    for(int i = 0; i < taskQuantity; i++) {
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_D);

                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_D);
                    }
                } else {
                    Logger.logInfo("Task Quantity is 1 skipping");
                }
                firstRow.invoke();

                Thread.sleep(500);

                Logger.logInfo("Starting all tasks");
                prismPanel.getButton("Start All").click();

                AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(prismPanel));
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }

        return false;
    }

    private Hyperlink getHyper(Panel panel) {
        try {
            return panel.getHyperlink(1);
        } catch (Exception  e) {

        }

        return null;
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

                getLogger().info("Stopping all tasks");
                panel.getButton("Stop All").click();

                getLogger().info("Deleting all tasks");
                panel.getButton("Delete All").click();

                getLogger().success("Deleted all tasks");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
