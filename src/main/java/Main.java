import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.impl.rest.WrathBot;
import com.glitchsoftware.autopilot.bot.types.BasicBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.security.Security;

import javax.swing.*;
import java.io.File;

/**
 * @author Brennan
 * @since 5/24/2021
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        if(!System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            JOptionPane.showMessageDialog(null, "Glitch only supports Windows!");
            return;
        }
        Security.start();

        AutoPilot.INSTANCE.start();

//        final RestBot restBot = new WrathBot();
//        restBot.setFile(new File("C:\\Users\\br3nn\\AppData\\Local\\Programs\\WrathAIO\\Wrath AIO.exe"));
//        restBot.runBot("footlocker.com", "glitchtest", 1);

    }

}
