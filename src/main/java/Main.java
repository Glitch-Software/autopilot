import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.security.Security;

import javax.swing.*;

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
    }

}
