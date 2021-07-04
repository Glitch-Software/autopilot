package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.SiteDetector;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.controls.*;
import org.checkerframework.checker.units.qual.K;
import org.sikuli.script.App;
import org.sikuli.script.Key;
import org.sikuli.script.Match;

import java.awt.*;
import java.awt.event.KeyEvent;
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
            final App whatBotApp = launchBot();

            getLogger().info("Waiting....");

            final Match match = getScreen().exists(getBasePath() + "task_groups.png");
            if(match == null) {
                getScreen().wait(getBasePath() + "tasks_button.png");
                getScreen().click(getBasePath() + "tasks_button.png");
            }
            getLogger().success("Found.");
            getScreen().wait(getBasePath() + "task_groups.png");

            getLogger().info("Finding " + site + " group....");
            final String groupButton = getBasePath() + SiteDetector.getGroup(site) + "_button.png";

            if(getScreen().exists( getBasePath() + SiteDetector.getGroup(site) + "_button-active.png") == null) {
                final Match groupMatch = getScreen().exists(groupButton);
                if(groupMatch != null) {
                    getLogger().success("Found " + site + " group.");
                    groupMatch.click();
                } else {
                    getLogger().error("Failed to find " + site + " group!");
                }
            } else {
                getLogger().success("Already in " + site + " group!");
            }

            getLogger().info("Finding SKU input...");
            Match skuMatch = getScreen().exists(getBasePath() + "sku_input.png");
            if(skuMatch == null) {
                getLogger().info("Failed to find SKU input... clicking 'Setup'");
                getScreen().wait(getBasePath() + "setup_button.png");
                getScreen().click(getBasePath() + "setup_button.png");

                getLogger().success("Clicked 'Setup' and found SKU input");
                skuMatch = getScreen().find(getBasePath() + "sku_input.png");
            }
            getLogger().info("Setting SKU " + task.getSku());
            skuMatch.click();

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            getLogger().success("Cleared SKU");

            skuMatch.type(task.getSku());
            getLogger().success("Successfully set SKU");

            getLogger().info("Setting Stop After.");

            final Match stopAfterButton = getScreen().exists(getBasePath() + "stop_after_button.png");

            if(stopAfterButton != null) {
                getScreen().mouseMove(stopAfterButton.x, stopAfterButton.y + 5);
                getScreen().click();

                getScreen().wait(getBasePath() + "5_min_button.png");
                getScreen().click(getBasePath() + "5_min_button.png");
            } else {
                getLogger().error("Failed to find 'Stop After'");
            }

            getLogger().info("Set Stop After.");

            getLogger().info("Creating Tasks...");
            getScreen().wait(getBasePath() + "create_tasks_button.png");
            getScreen().click(getBasePath() + "create_tasks_button.png");

            getScreen().wait(getBasePath() + "bulk_add.png");

            getScreen().wait(getBasePath() + "create_tasks.png");
            getScreen().click(getBasePath() + "create_tasks.png");
            getLogger().success("Created Tasks");
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return false;
    }
}
