package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.util.Logger;
import mmarquee.automation.controls.*;
import sun.rmi.runtime.Log;

import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Kodai")
public class KodaiBot extends BasicBot {

    @Override
    public boolean runBot(String site, String sku, String groupName, int taskQuantity) {
        try {
            if(getAutomation().findPane("Kodai") == null) {
                Logger.logInfo("[Bot Kodai] - Launching Kodai");
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            Logger.logInfo("[Bot Kodai] - Waiting for Kodai");
            Panel kodaiPanel = getAutomation().findPane("Kodai");
            while (kodaiPanel == null) {
                kodaiPanel = getAutomation().findPane("Kodai");
            }
            Logger.logInfo("[Bot Kodai] - Found Kodai");

            kodaiPanel.getElement().setFocus();

            Logger.logInfo("[Bot Kodai] - Found Kodai to load");
            TextBox automationTextBox = null;
            while (automationTextBox == null) {
                kodaiPanel = getAutomation().findPane("Kodai");
                if(kodaiPanel != null)
                    automationTextBox = kodaiPanel.findBox("RELEASES");
            }
            Logger.logInfo("[Bot Kodai] - Kodai loaded");
            automationTextBox.invoke();

            Thread.sleep(500);

            Logger.logInfo("[Bot Kodai] - Finding Group");
            kodaiPanel.getButton(groupName).click();
            Thread.sleep(500);
            final EditBox automationEditBox = kodaiPanel.getEditBox(0);
            automationEditBox.setValue(sku);
            Logger.logInfo("[Bot Kodai] - Inputting SKU");

            int saveIndex = 0;

            java.util.List<AutomationBase> children = kodaiPanel.getChildren(true);

            Logger.logInfo("[Bot Kodai] - Finding Button Indexes");
            for (final AutomationBase automationBase : children) {
                if (automationBase instanceof Button) {
                    if (automationBase.getName().equalsIgnoreCase("SAVE CHANGES")) {
                        ((Button) automationBase).click();
                        break;
                    }

                    saveIndex++;
                }
            }
            Logger.logInfo("[Bot Kodai] - Found Button indexes");

            Thread.sleep(500);

            Logger.logInfo("[Bot Kodai] - Creating Tasks");
            kodaiPanel.getButton(saveIndex + 1).click();

            Thread.sleep(500);

            Logger.logInfo("[Bot Kodai] - Setting Profiles");
            kodaiPanel.getCheckBox("USE ALL PROFILES.").toggle();

            Thread.sleep(500);

            Logger.logInfo("[Bot Kodai] - Setting Size");

            final TextBox textBox = kodaiPanel.getTextBox("Size");
            textBox.invoke();

            Thread.sleep(500);

            final List automationList = kodaiPanel.getList(4);
            automationList.getChildren(true).get(3).invoke();

            Logger.logInfo("[Bot Kodai] - Setting Quantity");
            final Spinner automationSpinner = kodaiPanel.getSpinner("1");
            automationSpinner.setValue(String.valueOf(taskQuantity));

            Logger.logSuccess("[Bot Kodai] - Created Tasks");
            kodaiPanel.getButton("CREATE TASK").click();
            kodaiPanel.getButton("GO BACK").click();

            kodaiPanel.getButton("SAVE CHANGES").click();

            Logger.logSuccess("[Bot Kodai] - Started Tasks");
            kodaiPanel.getButton("START").click();

            AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(kodaiPanel, saveIndex + 4));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static class DeleteThread implements Runnable {
        private final Panel automationPanel;
        private final int index;

        public DeleteThread(Panel automationPanel, int index) {
            this.automationPanel = automationPanel;
            this.index = index;
        }

        @Override
        public void run() {
            try {
                Logger.logSuccess("[Bot Kodai] - Waiting for Stop Timeout");

                TimeUnit.SECONDS.sleep(5);

                Logger.logSuccess("[Bot Kodai] - Stopping Tasks");

                final Button stopButton = automationPanel.findButton("STOP");
                if(stopButton != null) {
                    stopButton.click();
                }

                Thread.sleep(500);

                automationPanel.getButton(index).click();

                Thread.sleep(500);

                Logger.logSuccess("[Bot Kodai] - Deleting Tasks");

                automationPanel.getButton("YES, DELETE THEM ALL.").click();

                Logger.logSuccess("[Bot Kodai] - Resetting SKU");

                final EditBox automationEditBox = automationPanel.getEditBox(0);
                automationEditBox.setValue("");

                automationPanel.getButton(index - 4).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
