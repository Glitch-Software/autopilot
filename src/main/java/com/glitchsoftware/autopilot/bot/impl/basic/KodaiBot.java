package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import mmarquee.automation.controls.*;

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
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            Panel kodaiPanel = getAutomation().findPane("Kodai");
            while (kodaiPanel == null) {
                kodaiPanel = getAutomation().findPane("Kodai");
            }
            kodaiPanel.getElement().setFocus();

            TextBox automationTextBox = null;
            while (automationTextBox == null) {
                kodaiPanel = getAutomation().findPane("Kodai");
                if(kodaiPanel != null)
                    automationTextBox = kodaiPanel.findBox("RELEASES");
            }
            automationTextBox.invoke();

            Thread.sleep(500);

            kodaiPanel.getButton(groupName).click();
            Thread.sleep(500);
            final EditBox automationEditBox = kodaiPanel.getEditBox(0);
            automationEditBox.setValue(sku);

            int saveIndex = 0;

            java.util.List<AutomationBase> children = kodaiPanel.getChildren(true);

            for (final AutomationBase automationBase : children) {
                if (automationBase instanceof Button) {
                    if (automationBase.getName().equalsIgnoreCase("SAVE CHANGES")) {
                        ((Button) automationBase).click();
                        break;
                    }

                    saveIndex++;
                }
            }

            Thread.sleep(500);

            kodaiPanel.getButton(saveIndex + 1).click();

            Thread.sleep(500);

            kodaiPanel.getCheckBox("USE ALL PROFILES.").toggle();

            Thread.sleep(500);

            final TextBox textBox = kodaiPanel.getTextBox("Size");
            textBox.invoke();

            Thread.sleep(500);

            final List automationList = kodaiPanel.getList(4);
            automationList.getChildren(true).get(3).invoke();

            final Spinner automationSpinner = kodaiPanel.getSpinner("1");
            automationSpinner.setValue("4");

            kodaiPanel.getButton("CREATE TASK").click();
            kodaiPanel.getButton("GO BACK").click();

            kodaiPanel.getButton("SAVE CHANGES").click();

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
                TimeUnit.SECONDS.sleep(5);

                final Button stopButton = automationPanel.findButton("STOP");
                if(stopButton != null) {
                    stopButton.click();
                }

                Thread.sleep(500);

                automationPanel.getButton(index).click();

                Thread.sleep(500);

                automationPanel.getButton("YES, DELETE THEM ALL.").click();

                final EditBox automationEditBox = automationPanel.getEditBox(0);
                automationEditBox.setValue("");

                automationPanel.getButton(index - 4).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
