package com.glitchsoftware.autopilot.bot;

import com.google.gson.JsonObject;
import org.sikuli.script.App;

import java.io.File;
import java.util.Locale;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * This is our base Bot interface that all our bot types extend
 **/
public interface Bot {

    /**
     * The name of out bot
     * @return name of the bot
     */
    String getName();

    /**
     * The file path to the bot
     * @return bot file patch
     */
    File getFile();

    /**
     * Sets the bot file path
     * @param file the file path
     */
    void setFile(File file);

    /**
     * Our JSON object to save
     * @return save object
     */
    JsonObject toJSON();

    /**
     * To load our saved JSON
     * @param jsonObject saved JSON
     */
    void fromJSON(JsonObject jsonObject);

    default App launchBot() {
        App botApp = findApp(getName());

        if(botApp == null) {
            botApp = App.open(getFile().getAbsolutePath());
            botApp.waitForWindow();
        }
        botApp.focus();

        return botApp;
    }

    default App findApp(String name) {
        for(App app : App.getApps()) {
            if(app.getName().toLowerCase().contains(name.toLowerCase())) {
                return app;
            }
        }

        return null;
    }

}
