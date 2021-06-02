package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.util.Logger;
import com.google.gson.JsonObject;
import mmarquee.automation.controls.*;
import mmarquee.automation.controls.Button;
import mmarquee.automation.controls.Image;
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
        if(getSessionID().isEmpty() || getSessionID() == null) {
            JOptionPane.showMessageDialog(null, "No Session Prism Set", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            if(getAutomation().findPane("PrismAIO") == null) {
                Logger.logInfo("Launching PrismAIO");
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            Logger.logInfo("Waiting for PrismAIO");
            Panel prismPanel = getAutomation().findPane("PrismAIO");
            while (prismPanel == null) {
                prismPanel = getAutomation().findPane("PrismAIO");
            }
            Logger.logSuccess("Found Prism");

            Logger.logInfo("Waiting for PrismAIO to load");
            Button homeButton = prismPanel.findButton("Logo");
            while (homeButton == null) {
                prismPanel = getAutomation().findPane("PrismAIO");

                if(prismPanel != null)
                    homeButton = prismPanel.findButton("Logo");
            }
            Logger.logSuccess("Prism loaded");

            final String link = String.format("https://www.%s/product/~/%s.html", site, sku);

            Logger.logInfo("Sending SKU to Prism API");
            if(send(link)) {
                Logger.logSuccess("Sent to API");
                prismPanel.getElement().setFocus();

                Thread.sleep(500);

                Logger.logInfo("Finding table");
                AutomationBase table = null;
                for(AutomationBase automationBase : prismPanel.getChildren(true)) {
                    if(automationBase.getAriaRole().equalsIgnoreCase("grid")) {
                        table = automationBase;
                        Logger.logSuccess("Found table");
                    }
                }
                Thread.sleep(500);

                prismPanel.getButton("Stop All").click();

                AutomationBase firstRow = table.getChildren(true).get(0);
                firstRow.invoke();

                final Robot robot = new Robot();
                robot.setAutoDelay(10);

                Logger.logInfo("Duplicating tasks " + taskQuantity);
                for(int i = 0; i < taskQuantity; i++) {
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_D);

                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_D);

                    // Thread.sleep(1);
                }
                firstRow.invoke();

                Thread.sleep(500);

                Logger.logInfo("Starting all tasks");
                prismPanel.getButton("Start All").click();

                AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(prismPanel));
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

    private static class DeleteThread implements Runnable {
        private Panel panel;

        public DeleteThread(Panel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);

                Logger.logInfo("Stopping all tasks");
                panel.getButton("Stop All").click();

                Logger.logInfo("Deleting all tasks");
                panel.getButton("Delete All").click();

                Logger.logSuccess("Deleted all tasks");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
