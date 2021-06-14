package com.glitchsoftware.autopilot.bot.impl.basic;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.task.Task;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.UIAutomation;
import mmarquee.automation.controls.*;
import mmarquee.automation.controls.Button;
import mmarquee.automation.controls.Container;
import mmarquee.automation.controls.Panel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Wrath")
public class WrathBot extends BasicBot {

    @Override
    public boolean runBot(String site, Task task) {
        try {
            if(getAutomation().findPane("Wrath AIO") == null) {
                Logger.logInfo("[Bot Wrath] - Launching Wrath");
                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            Logger.logInfo("[Bot Wrath] - Waiting for Wrath");
            Panel wrathPanel = getAutomation().findPane("Wrath AIO");
            while (wrathPanel == null) {
                wrathPanel = getAutomation().findPane("Wrath AIO");
            }
            Logger.logInfo("[Bot Wrath] - Found Wrath");

            wrathPanel.getElement().setFocus();
            Thread.sleep(500);

            Document wrath = wrathPanel.getDocument(0);

            //button for tasks page
            wrath.getChildren(false).get(6).invoke();
            Thread.sleep(500);


//        AutomationBase groupObj = wrath.getChildren(false).get(39);
            ArrayList<Container> arr = childrenOfType(wrath, Container.class);
            AutomationBase groupObj = arr.get(1);
//        System.out.println(groupObj.getName());

            //First task group
            groupObj.getChildren(false).get(0).invoke();
            Thread.sleep(500);

            //create task button
            ArrayList<Button> btns = childrenOfType(wrath, Button.class);
            btns.get(5).invoke();
            Thread.sleep(500);


            //Site
            ArrayList<EditBox> edits = childrenOfType(wrath, EditBox.class);
            edits.get(1).setValue(site);
            Thread.sleep(500);

            ArrayList<Container> selection = childrenOfType(wrath, Container.class);
            selection.get(1).invoke();
            Thread.sleep(200);
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(300);

            edits.get(2).setValue(task.getSku());

            edits.get(5).setValue("Random");
            Thread.sleep(200);
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(300);

            ArrayList<Spinner> spinners = childrenOfType(wrath, Spinner.class);
            Spinner taskQty = spinners.get(0);
            taskQty.setValue(task.getTaskQuantity()+"");
            Thread.sleep(500);

            //Create all tasks
            btns = childrenOfType(wrath, Button.class);
            btns.get(btns.size() - 2).invoke();
            Thread.sleep(500);

            //Exit task creation menu
            btns.get(btns.size() - 4).invoke();
            Thread.sleep(500);


            AutoPilot.INSTANCE.getExecutorService().execute(new DeleteThread(wrath));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static <T> ArrayList<T> childrenOfType(AutomationBase ab, Class<T> t) {
        try {
            ArrayList<T> res = new ArrayList<>();
            for (AutomationBase el : ab.getChildren(false)) {
                if (el.getClass() == t) {
                    res.add((T) el);
                }
            }
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static class DeleteThread implements Runnable {
        private final Document wrath;


        public DeleteThread(Document doc) {
            this.wrath = doc;
        }

        @Override
        public void run() {
            try {
                Logger.logSuccess("[Bot Wrath] - Waiting for Stop Timeout");

                TimeUnit.MINUTES.sleep(6);

                Logger.logSuccess("[Bot Wrath] - Stopping Tasks");

                //Delete all tasks
                ArrayList<Button> btns;

                btns = childrenOfType(wrath, Button.class);
                btns.get(btns.size()-2).invoke();
                Thread.sleep(500);

                //Confirm Delete
                btns = childrenOfType(wrath, Button.class);
                btns.get(btns.size()-2).invoke();
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
