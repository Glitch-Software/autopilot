package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.controls.*;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("WhatBot")
public class WhatBotBot extends BasicBot {

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        try {

            if(getAutomation().findPane("What Bot") == null) {
                Logger.logInfo("[WhatBot] - Launching...");

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

            Hyperlink currentRelease = null;

            while (currentRelease == null) {
                currentRelease = whatBotPanel.findHyperLink(site);
            }
            currentRelease.invoke();
            Thread.sleep(500);

            final Hyperlink taskSetupTab = whatBotPanel.getHyperlink("Setup");
            taskSetupTab.invoke();
            Thread.sleep(500);

            final EditBox skuTextBox = whatBotPanel.getEditBox("PRODUCT SKU");
            skuTextBox.setValue(sku);
            Thread.sleep(500);

            final ComboBox stopAfter = whatBotPanel.getComboBox("STOP AFTER");
            stopAfter.invoke();
            Thread.sleep(500);

            final ListItem stopAfterItem = whatBotPanel.getList(0).getItem(5);
            stopAfterItem.invoke();
            Thread.sleep(500);

            final Button createMultipleTasks = whatBotPanel.getButton(3);
            createMultipleTasks.invoke();
            Thread.sleep(500);

            final ComboBox numTasksSelector = whatBotPanel.getComboBox("TASKS PER PROFILE");
            numTasksSelector.invoke();
            Thread.sleep(500);

            taskQuantity = Math.min(Math.max(taskQuantity, 0), 25);
            final ListItem numTasksItem = whatBotPanel.getList(0).getItem(taskQuantity - 1);
            numTasksItem.invoke();
            Thread.sleep(500);

            final Button createTasks = whatBotPanel.getButton("CREATE TASKS");
            createTasks.invoke();
            Thread.sleep(500);

            final Button startAllTasks = whatBotPanel.getButton(5);
            startAllTasks.invoke();
            //Thread.sleep(500);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
