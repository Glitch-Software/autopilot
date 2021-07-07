package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.SiteDetector;
import com.glitchsoftware.autopilot.util.Utils;
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
                final Match tasksButton = getScreen().exists(getBasePath() + "tasks_button.png");

                if(tasksButton != null) {
                    getScreen().wait(getBasePath() + "tasks_button.png");
                    getScreen().click(getBasePath() + "tasks_button.png");
                }
            }
            getLogger().success("Found.");
            getScreen().wait(getBasePath() + "task_groups.png");

            getLogger().info("Finding " + site + " group....");
            final Match groupMatch = getScreen().exists(getBasePath() + SiteDetector.getGroup(site) + "_button.png");
            if(groupMatch != null) {
                getLogger().success("Found " + site + " group.");
                groupMatch.click();
            } else {
                getLogger().error("Failed to find " + site + " group!");
            }

            getLogger().info("Finding SKU input...");
            Match skuMatch = getScreen().exists(getBasePath() + "sku_input.png");
            if(skuMatch == null) {
                getLogger().info("Failed to find SKU input... clicking 'Setup'");

                Match setupButton = getScreen().exists(getBasePath() + "setup_button.png");
                if(setupButton != null) {
                    setupButton.click();
                }

                getLogger().success("Clicked 'Setup' and found SKU input");
                skuMatch = getScreen().find(getBasePath() + "sku_input.png");
            }
            getLogger().info("Setting SKU " + task.getSku());
            skuMatch.click();

            Utils.deleteAll();
            getLogger().success("Cleared SKU");

            skuMatch.type(task.getSku());
            getLogger().success("Successfully set SKU");

            getLogger().info("Creating Tasks...");
            final Match createTasksButton = getScreen().exists(getBasePath() + "create_tasks_button.png");
            if(createTasksButton != null) {
                createTasksButton.click();

                getScreen().wait(getBasePath() + "bulk_add.png");

                final Match createTasks = getScreen().exists(getBasePath() + "create_tasks.png");
                if(createTasks != null) {
                    createTasks.click();

                    getLogger().success("Created Tasks");

                    final Match startTasks = getScreen().exists(getBasePath() + "start_tasks.png");
                    if(startTasks != null) {
                        startTasks.click();

                        final Match tasksButton = getScreen().exists(getBasePath() + "start_tasks.png");
                        if(tasksButton != null) {
                            tasksButton.click();

                            final Match tasks = getScreen().exists(getBasePath() + "tasks.png");
                            if(tasks != null) {
                                tasks.click();
                            }

                            new Thread(new DeleteThread(whatBotApp)).start();
                        }
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
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

                getLogger().info("Stopping Tasks...");
                final Match stopButton = getScreen().exists(getBasePath() + "stop_button.png");
                if(stopButton != null) {
                    stopButton.click();
                    getLogger().success("Stopped Tasks");
                } else {
                    getLogger().error("Failed to find 'Stop Button'");
                }

                getLogger().info("Deleting Tasks...");
                Match randomTask = getScreen().exists(getBasePath() + "random.png");

                while (randomTask != null) {
                    randomTask.hover();

                    Match deleteTask = getScreen().exists(getBasePath() + "delete_task_button.png");

                    if(deleteTask != null) {
                        deleteTask.click();

                        final Match deleteButton = getScreen().exists(getBasePath() + "delete_button.png");

                        if(deleteButton != null)
                            Utils.enter();
                    } else {
                        getLogger().error("Failed to find 'Delete Task Button'");
                    }


                    randomTask = getScreen().exists(getBasePath() + "random.png");
                }
                getLogger().success("Deleted Tasks");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
