package com.glitchsoftware.autopilot.app;

import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.serializer.Serializer;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.websocket.api.Session;

import static spark.Spark.*;

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
    private Session session;

    /**
     * Starts our websocket and initializes the endpoint "/socket" with our handler {@link WebSocketHandler}
     * also sets our port to 8080 and initializes Spark
     */
    public void start() {
        webSocket("", new WebSocketHandler());
        port(4317);
        init();
    }

    public void send(Packet packet) {
        try {
            System.out.println("sending " + packet.getName());
            session.getRemote().sendString(Serializer.serialize(packet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
