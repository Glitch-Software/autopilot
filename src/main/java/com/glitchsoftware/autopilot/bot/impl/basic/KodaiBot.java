package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.BotLogger;
import mmarquee.automation.controls.*;

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
            boolean response = false;
            while (task.isRunning()) {

                if(!getFile().exists()) {
                    getLogger().error("Failed to find file!");
                    task.setRunning(false);
                    break;
                }

                if(getAutomation().findPane("Kodai") == null) {
                    getLogger().info("Launching...");
                    final Application application =
                            new Application(
                                    new ElementBuilder()
                                            .automation(getAutomation())
                                            .applicationPath(getFile().getAbsolutePath()));
                    application.launchOrAttach();

                    application.waitForInputIdle(Application.SHORT_TIMEOUT);

                    System.out.println(application.getIsAttached());
                }

                getLogger().info("Waiting...");
                Panel kodaiPanel = getAutomation().findPane("Kodai");
                while (kodaiPanel == null) {
                    kodaiPanel = getAutomation().findPane("Kodai");
                }
                getLogger().info("Found!");

                kodaiPanel.getElement().setFocus();

                getLogger().info("Loading...");
                if(kodaiPanel.findButton(site) == null) {
                    TextBox automationTextBox = null;
                    while (automationTextBox == null) {
                        kodaiPanel = getAutomation().findPane("Kodai");
                        if(kodaiPanel != null)
                            automationTextBox = kodaiPanel.findBox("RELEASES");
                    }
                    automationTextBox.invoke();
                }
                getLogger().info("Loaded");

                Thread.sleep(500);

                getLogger().info("Finding Site Group");

                final Button button = kodaiPanel.findButton(site);

                if(button == null) {
                    getLogger().error("Failed to find site group (" + site + "). Please follow our setup guides!");
                    return false;
                }
                button.click();

                Thread.sleep(500);
                final EditBox automationEditBox = kodaiPanel.getEditBox(0);
                automationEditBox.setValue(task.getSku());
                getLogger().info("Inputting SKU");

                java.util.List<AutomationBase> children = kodaiPanel.getChildren(true);

                int saveIndex = 0;

                getLogger().info("Finding Button Indexes");
                for (final AutomationBase automationBase : children) {
                    if (automationBase instanceof Button) {
                        if (automationBase.getName().equalsIgnoreCase("SAVE CHANGES")) {
                            ((Button) automationBase).click();
                            break;
                        }

                        saveIndex++;
                    }
                }
                getLogger().info("Found Button indexes");

                Thread.sleep(500);

                getLogger().info("Creating Tasks");
                kodaiPanel.getButton(saveIndex + 1).click();

                Thread.sleep(500);

                getLogger().info("Setting Profiles");
                kodaiPanel.getCheckBox("USE ALL PROFILES.").toggle();

                Thread.sleep(500);

                getLogger().info("Setting Size");

                final TextBox textBox = kodaiPanel.getTextBox("Size");
                textBox.invoke();

                Thread.sleep(500);

                final List automationList = kodaiPanel.getList(4);
                automationList.getChildren(true).get(3).invoke();

                getLogger().info("Setting Quantity (" + task.getTaskQuantity() + ")");
                final Spinner automationSpinner = kodaiPanel.getSpinner("1");
                automationSpinner.setValue(String.valueOf(task.getTaskQuantity()));

                kodaiPanel.getButton("CREATE TASK").click();
                kodaiPanel.getButton("GO BACK").click();
                getLogger().success("Created Tasks");

                kodaiPanel.getButton("SAVE CHANGES").click();

                getLogger().success("[Kodai] - Started Tasks");
                kodaiPanel.getButton("START").click();

                response = true;
                AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(kodaiPanel, site, saveIndex + 4, task));
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void deleteTasks(Panel automationPanel, String group, int index, Task task) {
        try {
            if(automationPanel == null) {
                return;
            }
            final long deleteTimeout = AutoPilot.INSTANCE.getConfig().getDeleteTimeout();
            getLogger().info("Waiting for Delete Timeout (" + deleteTimeout + " minutes)");
            TimeUnit.MINUTES.sleep(deleteTimeout);

            TimeUnit.MINUTES.sleep(AutoPilot.INSTANCE.getConfig().getDeleteTimeout());

            final Button button = automationPanel.findButton(group);

            if(button == null) {
                getLogger().error("Failed to find (" + group + ") button!");
                return;
            }
            button.click();

            getLogger().success("Stopping Tasks");

            final Button stopButton = automationPanel.findButton("STOP");
            if(stopButton != null) {
                stopButton.click();
                getLogger().success("Stopped Tasks");
            }

            Thread.sleep(500);

            automationPanel.getButton(index).click();

            Thread.sleep(500);

            getLogger().success("Deleting Tasks");

            automationPanel.getButton("YES, DELETE THEM ALL.").click();

            getLogger().success("Resetting SKU");

            final EditBox automationEditBox = automationPanel.getEditBox(0);
            automationEditBox.setValue("");

            automationPanel.getButton(index - 4).click();

            task.setRunning(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DeleteThread implements Runnable {
        private final Panel automationPanel;
        private final String group;
        private final int index;

        private Task task;

        public DeleteThread(Panel automationPanel, String group, int index, Task task) {
            this.automationPanel = automationPanel;
            this.group = group;
            this.index = index;
            this.task = task;
        }

        @Override
        public void run() {
            deleteTasks(automationPanel, group, index, task);
        }
    }
}
