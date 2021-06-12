package com.glitchsoftware.autopilot.app;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.serializer.Serializer;
import lombok.Getter;
import lombok.Setter;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.handler.StaticFileHandler;


/**
 * @author Brennan
 * @since 5/25/2021
 *
 * Our WebSocket class
 **/
@Getter
@Setter
public class WebSocket {

    /**
     * Our websocket session
     */
    private WebSocketConnection session;

    private boolean authed = false;

    /**
     * Starts our websocket and initializes the endpoint "/socket" with our handler {@link WebSocketHandler}
     * also sets our port to 8080 and initializes Spark
     */
    public void start() {
        WebServer webServer = WebServers.createWebServer(4317)
                .add("/ws", new WebSocketHandler())
                .add(new StaticFileHandler("/web"));
        webServer.start();
        System.out.println("App running on " + webServer.getPort());
    }


    public void send(Packet packet) {
        try {
            if(session != null)
                session.send(Serializer.serialize(packet));
        } catch (Exception e) {
        }
    }
}
