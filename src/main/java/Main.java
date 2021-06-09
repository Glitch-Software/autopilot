import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.impl.rest.PrismBot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.util.Utils;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class Main {

    public static void main(String[] args) throws Exception {
//        if(!System.getProperty("os.name").contains("win")) {
//            JOptionPane.showMessageDialog(null, "Glitch only cusu");
//            return;
//        }
        System.out.println(System.getProperty("os.name").contains("win"));
        AutoPilot.INSTANCE.start();

//        final ConnectionBot connectionBot = new PrismBot();
//        connectionBot.setSessionID("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjBhZWFjOTJlLTQyOGQtNGJkZi04MzY0LTU5MTY4MWQzMWM0YiIsImlhdCI6MTYyMjMxMjAyOCwiZXhwIjoxNjI0OTA0MDI4fQ.fKWlxEXElK9OLLYWu1_ExAH4S4rnuxS8Jm6YxtaJ2mU");
//        connectionBot.setFile(new File("C:\\Users\\br3nn\\AppData\\Local\\Programs\\PrismAIO\\PrismAIO.exe"));
//        connectionBot.runBot("footlocker.com", "glitchtest", 50);

//        final BasicBot basicBot = new WhatBotBot();
//        System.out.println(basicBot.getName());
//        basicBot.setFile(new File("C:\\Users\\br3nn\\Desktop\\What Bot 1.0 Alpha\\What Bot Alpha.exe"));
//        basicBot.runBot("", "glitchtest", "test", 4);

        //WhatBot.runBot("test", "glitchtest", 4);
        //Kodai.runBot("glitchtest", "test");

    }

}
