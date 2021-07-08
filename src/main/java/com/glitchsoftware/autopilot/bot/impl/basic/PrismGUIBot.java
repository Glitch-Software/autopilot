package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 6/16/2021
 **/
@BotManifest("PrismGUI")
public class PrismGUIBot extends BasicBot {

    @Override
    public boolean runBot(String site, Task task) {
//        try {
//            if(getAutomation().findPane("PrismAIO") == null) {
//                getLogger().info("Launching....");
//                final Application application =
//                        new Application(
//                                new ElementBuilder()
//                                        .automation(getAutomation())
//                                        .applicationPath(getFile().getAbsolutePath()));
//                application.launchOrAttach();
//
//                application.waitForInputIdle(Application.SHORT_TIMEOUT);
//            }
//            Panel prismPanel = getAutomation().findPane("PrismAIO");
//            while (prismPanel == null) {
//                prismPanel = getAutomation().findPane("PrismAIO");
//            }
//            prismPanel.getElement().setFocus();
//
//            final Robot robot = new Robot();
//
//            for(ListItem listItem : prismPanel.getList(0).getItems()) {
//                for(AutomationBase automationBase : listItem.getChildren(true)) {
//                    if(automationBase.getName().contains(site)) {
//                        robot.mouseMove(automationBase.getClickablePoint().x, automationBase.getClickablePoint().y);
//                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//
//                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//                    }
//                }
//            }
//
//            Thread.sleep(500);
//
//            prismPanel.getEditBox("24300657").setValue(task.getSku());
//            prismPanel.getButton(4).click();
//
//            prismPanel = getAutomation().findPane("PrismAIO");
//
//            final Custom taskDialog = prismPanel.getCustom(0);
//            taskDialog.getSpinner(1).setValue(String.valueOf(task.getTaskQuantity()));
//            final Button button = taskDialog.getButton(0);
//            button.click();
//
//            robot.mouseMove(button.getClickablePoint().x + 200, button.getClickablePoint().y);
//            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//
//            prismPanel = getAutomation().findPane("PrismAIO");
//
//            prismPanel.getButton("Start All").click();
//
//
//            new Thread(new DeleteThread(prismPanel)).start();
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }

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

                TimeUnit.SECONDS.sleep(5);

//                panel.getButton("Delete All").click();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
