package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.SiteDetector;
import com.glitchsoftware.autopilot.util.Utils;
import com.glitchsoftware.autopilot.util.logger.BotLogger;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.controls.*;
import org.opencv.core.Mat;
import org.sikuli.script.App;
import org.sikuli.script.Match;

import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Kodai")
public class KodaiBot extends BasicBot {

    @Override
    public boolean runBot(String site, Task task) {

        try {
            final App kodaiApp = launchBot();

            getLogger().info("Waiting....");

            final Match kodaiIcon = getScreen().exists(getBasePath() + "kodai_icon.png");
            if(kodaiIcon != null) {
                final Match releasesButton = getScreen().exists(getBasePath() + "releases_button.png");
                if(releasesButton != null) {
                    getLogger().success("Found 'Releases' finding task group...");
                    releasesButton.click();
                } else {
                    getLogger().error("Failed to find 'Releases'");
                    return false;
                }
            } else {
                final Match taskGroupButton = getScreen().exists(getBasePath() + "task_group_button.png");

                if(taskGroupButton == null) {
                    getLogger().error("Not on 'Dashboard' or 'Releases' Page");

                    return false;
                }

                getLogger().success("Already on 'Releases' Page");
            }

            final String group = getBasePath() + SiteDetector.getGroup(site) + "_button.png";

            final Match groupButton = getScreen().exists(group);

            if(groupButton != null) {
                getLogger().success("Found '" + site + "' task group.");
                groupButton.click();
            } else {
                getLogger().error("Failed to find '" + site + "' task group!");
                return false;
            }

            final Match skuInput = getScreen().exists(getBasePath() + "sku_input.png");
            if(skuInput != null) {
                getLogger().success("Found SKU input...");
                skuInput.click();
                Utils.deleteAll();
                skuInput.type(task.getSku());

                final Match saveChanges = getScreen().exists(getBasePath() + "save_changes_button.png");
                if(saveChanges != null) {
                    getLogger().success("Saved Changes");
                    saveChanges.click();

                    final Match newTaskButton = getScreen().exists(getBasePath() + "new_task.png");
                    if(newTaskButton != null) {
                        getLogger().success("Creating Tasks...");
                        newTaskButton.click();

                        final Match useAllProfiles = getScreen().exists(getBasePath() + "use_all_profiles.png");
                        if(useAllProfiles != null) {
                            getLogger().success("Set Profiles");
                            useAllProfiles.click();

                            final Match taskQuantity = getScreen().exists(getBasePath() + "task_quantity.png");
                            if(taskQuantity != null) {
                                getLogger().success("Setting Task Quantity");
                                taskQuantity.click();
                                Utils.deleteAll();
                                taskQuantity.type(String.valueOf(task.getTaskQuantity()));

                                final Match sizes = getScreen().exists(getBasePath() + "size_button.png");
                                if(sizes != null) {
                                    getLogger().success("Setting Sizes");
                                    sizes.click();

                                    getScreen().wait(getBasePath() + "sizes.png");

                                    final Match randomSize = getScreen().exists(getBasePath() + "random_button.png");
                                    if(randomSize != null) {
                                        getLogger().success("Set size to 'Random'");
                                        randomSize.click();

                                        final Match createTask = getScreen().exists(getBasePath() + "create_task.png");

                                        if(createTask != null) {
                                            getLogger().success("Created Task");
                                            createTask.click();

                                            final Match goBackButton = getScreen().exists(getBasePath() + "go_back.png");
                                            if(goBackButton != null) {
                                                goBackButton.click();

                                                saveChanges.click();

                                                final Match startButton = getScreen().exists(getBasePath() + "start_button.png");
                                                if(startButton != null) {
                                                    getLogger().success("Starting Tasks.");
                                                    startButton.click();

                                                    new Thread(new DeleteThread(kodaiApp)).start();
                                                } else {
                                                    getLogger().error("Failed to find 'Start'");
                                                }
                                            } else {
                                                getLogger().error("Failed to find 'Go Back'");
                                            }
                                        } else {
                                            getLogger().error("Failed to find 'Create Task'");
                                        }
                                    } else {
                                        getLogger().error("Failed to find 'Random'");
                                    }
                                } else {
                                    getLogger().error("Failed to find 'Sizes'");
                                }
                            } else {
                                getLogger().error("Failed to find 'Task Quantity'");
                            }
                        } else {
                            getLogger().error("Failed to find 'Use ALl Profiles'");
                        }
                    } else {
                        getLogger().error("Failed to find 'New Task'");
                    }
                } else {
                    getLogger().error("Failed to find 'Save Changes'");
                }
            } else {
                getLogger().error("Failed to find 'SKU input'");
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
                Logger.logInfo("Waiting for Delete Timeout.");

                TimeUnit.SECONDS.sleep(5);

                final Match stopButton = getScreen().exists(getBasePath() + "stop_button.png");

                if(stopButton != null) {
                    stopButton.click();

                    final Match clearButton = getScreen().wait(getBasePath() + "clear_button.png");
                    if(clearButton != null) {
                        clearButton.click();

                        getScreen().wait(getBasePath() + "delete_dialog.png");

                        final Match confirmDelete = getScreen().exists(getBasePath() + "confirm_delete.png");
                        if(confirmDelete != null) {
                            getLogger().success("Deleted all tasks");
                            confirmDelete.click();
                        } else {
                            getLogger().error("Failed to find 'Confirm Delete'");
                        }
                    } else {
                        getLogger().error("Failed to find 'Clear'");
                    }

                } else {
                    getLogger().error("Failed to find 'Stop'");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
