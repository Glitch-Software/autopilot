package com.glitchsoftware.autopilot.app;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.serializer.Serializer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * our websocket handler handles our connection, disconnect, and our packet serialization
 **/
@WebSocket
public class WebSocketHandler {

    /**
     *
     * when our react-app connects to our a websocket we set our session.
     *
     * @param session - our webhook session
     * @throws IOException - throws if connection is not handled properly
     */
    @OnWebSocketConnect
    public void handleConnection(Session session) throws IOException {
        AutoPilot.INSTANCE.getWebSocket().setSession(session);
        System.out.println("Connected to websocket");
    }

    /**
     *
     * when our react-app disconnects from our websocket we close the java app.
     *
     * @param session - our webhook session
     * @param statusCode - the status code given
     * @param reason - the reason our client disconnected
     */
    @OnWebSocketClose
    public void handleDisconnect(Session session, int statusCode, String reason) {
        System.out.println("disconnected");
      // System.exit(-1);
    }

    /**
     *
     * when our react-app sends a message we deserialize the input JSON to a {@link Packet} object
     *
     * @param session - our webhook session
     * @param message - packet data sent through our websocket
     * @throws IOException - thrown if packet serialization has failed
     */
    @OnWebSocketMessage
    public void handlePacketMessage(Session session, String message) throws IOException {
        final Packet packet = Serializer.deserialize(message);

        for(Command command : AutoPilot.INSTANCE.getWebSocketCommandManger().getCommands()) {
            if(command.getPacketName().equalsIgnoreCase(packet.getName()))
                command.execute(packet);
        }
    }

}
