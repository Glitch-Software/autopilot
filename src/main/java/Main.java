import com.glitchsoftware.autopilot.bot.impl.basic.WhatBotBot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;

import java.io.File;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class Main {

    public static void main(String[] args) {
       // AutoPilot.INSTANCE.start();

        final BasicBot basicBot = new WhatBotBot();
        System.out.println(basicBot.getName());
        basicBot.setFile(new File("C:\\Users\\br3nn\\Desktop\\What Bot 1.0 Alpha\\What Bot Alpha.exe"));
        basicBot.runBot("", "glitchtest", "test", 4);

        //WhatBot.runBot("test", "glitchtest", 4);
        //Kodai.runBot("glitchtest", "test");

    }

}
