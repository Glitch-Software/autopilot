package com.glitchsoftware.autopilot.util;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.bot.types.rest.types.ConnectionBot;
import com.google.gson.JsonParser;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.net.callback.CanSetCookieCallback;
import com.teamdev.jxbrowser.net.internal.rpc.ResponseBytesReceived;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class BotConnection {
    private final BotLogin botLogin;

    public Engine engine;
    public Browser browser;
    private JFrame frame;

    public BotConnection(String botLogin, ConnectionBot connectionBot) {
        this.botLogin = BotLogin.getLogin(botLogin);

        this.engine = Engine.newInstance(EngineOptions
                .newBuilder(RenderingMode.HARDWARE_ACCELERATED)
                .licenseKey("1BNDIEOFAYYSP2G3J3TWXYC49IRZCZBBQSBVC0YBDE59PPKDIRBIQYKZOXP2Y1AH98RDZQ").build());

        createWindow();

        this.browser.navigation().loadUrl(this.botLogin.getLogin());

        final AtomicBoolean added = new AtomicBoolean(false);

        if(this.botLogin == BotLogin.CYBER) {
            this.engine.network().set(CanSetCookieCallback.class, cookies -> {
                if(cookies.cookie().name().equalsIgnoreCase("dashboard_session")) {
                    setupBot(connectionBot, cookies.cookie().value());
                }

                return CanSetCookieCallback.Response.can();
            });
        } else {
            this.engine.network().on(ResponseBytesReceived.class, (bytesReceived) -> {
                try {
                    if(this.botLogin == BotLogin.PRISM) {
                        if (bytesReceived.urlRequest().url().contains("https://api.prismaio.com/users/machines")) {
                            for(Frame frame : browser.frames()) {
                                if(frame.localStorage().item("token").isPresent()) {

                                    if(!added.get()) {
                                        setupBot(connectionBot, frame.localStorage().item("token").get());
                                        added.set(true);
                                    }
                                }
                            }
                        }
                    } else if(this.botLogin == BotLogin.KYLIN) {
                        if (bytesReceived.urlRequest().url().contains("https://www.kylinbot.io/api/userInfo")) {
                            for(Frame frame : browser.frames()) {
                                if(frame.localStorage().item("kylin_dashboard_access_token").isPresent()) {

                                    if(!added.get()) {
                                        final String token = JsonParser
                                                .parseString(frame.localStorage().item("kylin_dashboard_access_token").get())
                                                .getAsJsonObject().get("token").getAsString();

                                        setupBot(connectionBot,
                                                token);
                                        added.set(true);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private void setupBot(ConnectionBot connectionBot, String sessionID) {
        connectionBot.setSessionID(sessionID);

        AutoPilot.INSTANCE.getBotManager().saveBot(connectionBot);
        Logger.logSuccess("[Bot Connection] - " + connectionBot.getName() + " Connected");
        this.frame.dispose();
    }

    private void createWindow() {
        this.browser = this.engine.newBrowser();
        BrowserView browserView = BrowserView.newInstance(browser);
        this.frame = new JFrame("Glitch: Connection");
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                BotConnection.this.engine.close();
            }
        });

        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.add(browserView, "Center");
        this.frame.setSize(360, 550);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    @Getter
    public enum BotLogin {
        PRISM("Prism","https://dashboard.prismaio.com/"),
        CYBER("Cybersole", "https://cybersole.io/dashboard"),
        KYLIN("Kylin", "https://dashboard.kylinbot.io/");

        private String name;
        private String login;

        BotLogin(String name, String login) {
            this.name = name;
            this.login = login;
        }

        public static BotLogin getLogin(String name) {
            for(BotLogin botLogin : values()) {
                if(botLogin.getName().equalsIgnoreCase(name))
                    return botLogin;
            }

            return null;
        }
    }
}
