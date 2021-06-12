package com.glitchsoftware.autopilot.bot.impl.rest;

import com.glitchsoftware.autopilot.bot.annotations.BotManifest;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.glitchsoftware.autopilot.util.logger.Logger;
import mmarquee.automation.AutomationException;
import mmarquee.automation.controls.Application;
import mmarquee.automation.controls.ElementBuilder;
import mmarquee.automation.controls.Window;
import okhttp3.Cookie;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;

/**
 * @author Brennan
 * @since 5/27/2021
 **/
@BotManifest("Cybersole")
@RestManifest("https://cybersole.io/dashboard/tasks?quicktask=%s")
public class CybersoleBot extends ConnectionBot {

    @Override
    public void onLoad() {
    }

    @Override
    public boolean runBot(String site, String sku, int taskQuantity) {
        if(getSessionID().isEmpty() || getSessionID() == null) {
            JOptionPane.showMessageDialog(null, "No Session Cyber Session Set", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            if(findWindow("CyberAIO") == null) {
                Logger.logInfo("[Cyber] - Launching...");

                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(getAutomation())
                                        .applicationPath(getFile().getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            Logger.logInfo("[Cyber] - Waiting...");
            Window cybersoleWindow = findWindow("CyberAIO");
            while (cybersoleWindow == null) {
                cybersoleWindow = findWindow("CyberAIO");
            }
            Logger.logSuccess("[Cyber] - Found");

            final String link = String.format("https://www.%s.com/product/~/%s.html", site, sku);

            Logger.logInfo("[Cyber] - Sending SKU to API");
            for(int i = 0; i < taskQuantity; i++)
                send(String.format(getApiUrl(), site + ":" + link));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }



        return false;
    }

    public Window findWindow(String name) throws AutomationException {
        for(Window window : getAutomation().getDesktopWindows()) {
            if(window.getName().contains(name))
                return window;
        }

        return null;
    }

    private boolean send(String link) {
        try {
            final Cookie dashboardSession = new Cookie.Builder()
                    .domain("cybersole.io")
                    .path("/")
                    .name("dashboard_session")
                    .value(getSessionID())
                    .httpOnly()
                    .secure()
                    .build();

            final Request request = new Request.Builder()
                    .header("Cookie", dashboardSession.toString())
                    .url(link)
                    .get()
                    .build();

            try(Response response = getOkHttpClient().newCall(request).execute()) {
                if(response.code() == 200) {
                    return !response.body().string().contains("discord");
                } else {
                    Logger.logError("[Cyber] - Received (" + response.code() + "), please check your session ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
