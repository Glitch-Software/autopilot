import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.impl.rest.WrathBot;
import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.glitchsoftware.autopilot.security.Security;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

//        Screen s = new Screen();
//
//        final URL resourceFolderURL = Main.class.getClassLoader().getResource("images");
//        final String basePath = resourceFolderURL.toURI().getPath() + "/wrath/";
//
//        try {
//            if(!findApp("Wrath")) {
//                App app = App.open("C:\\Users\\br3nn\\AppData\\Local\\Programs\\WrathAIO\\Wrath AIO.exe");
//                app.waitForWindow();
//                app.focus();
//            } else {
//                focusApp("Wrath");
//            }
//
//            s.wait(basePath + "taskbutton.png");
//            s.click(basePath + "taskbutton.png");
//
//            if(send("https://footlocker.com/~/glitch.html")) {
//                s.wait(basePath + "group.png");
//                s.click(basePath + "group.png");
//
//                TimeUnit.SECONDS.sleep(7);
//
//                s.click(basePath + "deleteall.png");
//
//                s.wait(basePath + "delete.png");
//
//                s.click(basePath + "delete.png");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        final RestBot restBot = new WrathBot();
//        restBot.setFile(new File("C:\\Users\\br3nn\\AppData\\Local\\Programs\\WrathAIO\\Wrath AIO.exe"));
//        restBot.runBot("footlocker.com", "glitchtest", 1);

    }

    private static boolean send(String link) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(String.format("http://localhost:32441/qt?input=%s", link)).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = in.readLine().replace("Task", "")
                        .replaceAll("created", "")
                        .replaceAll("successfully", "")
                        .replaceAll(" ", "");

                //getLogger().info("Successfully created (" + line.split(",").length + ") tasks");
                connection.disconnect();
                return true;
            }

            connection.disconnect();
            return false;
        } catch (Exception e) {
            if(e instanceof NoRouteToHostException) {
                //getLogger().error("Request blocked by Firewall");
            } else {
                //getLogger().error("Error trying to send to API: " + e.getMessage());
            }
        }

        return false;
    }

    public static void focusApp(String name) {
        for(App app : App.getApps()) {
            if(app.getName().contains(name))
                app.focus();
        }
    }

    public static boolean findApp(String name) {
        for(App app : App.getApps()) {
            if(app.getName().contains(name)) {
                return true;
            }
        }

        return false;
    }

}
