package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.controls.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("WhatBot")
public class WhatBotBot extends BasicBot {

    @Override
    public boolean runBot(String site, Task task) {
        try {
            if(getAutomation().findPane("What Bot") == null) {
                Logger.logInfo("[WhatBot] - Launching...");

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

            Logger.logInfo("[WhatBot] - Waiting...");
            Panel whatBotPanel = getAutomation().findPane("What Bot");
            while (whatBotPanel == null) {
                whatBotPanel = getAutomation().findPane("What Bot");
            }
            whatBotPanel.getElement().setFocus();

            Logger.logInfo("[WhatBot] - Finding site group...");

            Hyperlink currentRelease = null;

            while (currentRelease == null) {
                currentRelease = whatBotPanel.findHyperLink(site);
            }
            currentRelease.invoke();
            Thread.sleep(500);
            Logger.logInfo("[WhatBot] - Found Site Group");

            Logger.logInfo("[WhatBot] - Setting up");

            final Hyperlink taskSetupTab = whatBotPanel.getHyperlink("Setup");
            taskSetupTab.invoke();
            Thread.sleep(500);

            Logger.logInfo("[WhatBot] - Inputting SKU");

            final EditBox skuTextBox = whatBotPanel.getEditBox("PRODUCT SKU");
            skuTextBox.setValue(task.getSku());
            Thread.sleep(500);

            Logger.logInfo("[WhatBot] - Setting Stop");
            final ComboBox stopAfter = whatBotPanel.getComboBox("STOP AFTER");
            stopAfter.invoke();
            Thread.sleep(500);

            final ListItem stopAfterItem = whatBotPanel.getList(0).getItem(5);
            stopAfterItem.invoke();
            Thread.sleep(500);

            final Button createMultipleTasks = whatBotPanel.getButton(3);
            createMultipleTasks.invoke();
            Thread.sleep(500);

            Logger.logInfo("[WhatBot] - Setting Profiles");
            final ComboBox numTasksSelector = whatBotPanel.getComboBox("TASKS PER PROFILE");
            numTasksSelector.invoke();
            Thread.sleep(500);

            task.setTaskQuantity(Math.min(Math.max(task.getTaskQuantity(), 0), 25));
            Logger.logInfo("[WhatBot] - Setting Task Quantity (" + task.getTaskQuantity() + ")");
            final ListItem numTasksItem = whatBotPanel.getList(0).getItem(task.getTaskQuantity() - 1);
            numTasksItem.invoke();
            Thread.sleep(500);

            Logger.logSuccess("[WhatBot] - Creating Task");
            final Button createTasks = whatBotPanel.getButton("CREATE TASKS");
            createTasks.invoke();
            Thread.sleep(500);

            Logger.logSuccess("[WhatBot] - Starting Tasks");
            final Button startAllTasks = whatBotPanel.getButton(5);
            startAllTasks.invoke();
            //Thread.sleep(500);

            final long deleteTimeout = AutoPilot.INSTANCE.getConfig().getDeleteTimeout();
            getLogger().info("Waiting for Delete Timeout (" + deleteTimeout + " minutes)");
            TimeUnit.MINUTES.sleep(deleteTimeout);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
