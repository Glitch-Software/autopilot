import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.impl.basic.WhatBotBot;
import com.glitchsoftware.autopilot.bot.impl.rest.OminousBot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.security.Security;
import com.glitchsoftware.autopilot.task.Task;

import javax.swing.*;
import java.io.File;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class Main {

    public static void main(String[] args) throws Exception {
//        if(!System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
//            JOptionPane.showMessageDialog(null, "Glitch only supports Windows!");
//            return;
//        }
//        Security.start();
//
//        AutoPilot.INSTANCE.start();

//        final RestBot restBot = new OminousBot();
//        restBot.setFile(new File(""));
//        restBot.runBot("footlocker.com", "glitch-test", 1);

        final BasicBot bot = new WhatBotBot();
        bot.setFile(new File("C:\\Users\\Administrator\\Desktop\\What Bot 1.0 Alpha\\What Bot Alpha.exe"));
        bot.runBot("footlocker.com", new Task("glitchtest", null, 1));
    }

}
