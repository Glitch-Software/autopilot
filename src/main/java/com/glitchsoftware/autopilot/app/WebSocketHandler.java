package com.glitchsoftware.autopilot.app;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.serializer.Serializer;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

import java.io.IOException;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * our websocket handler handles our connection, disconnect, and our packet serialization
 **/
//@WebSocket
public class WebSocketHandler extends BaseWebSocketHandler {

    @Override
    public void onOpen(WebSocketConnection connection) throws Exception {
        super.onOpen(connection);
        AutoPilot.INSTANCE.getWebSocket().setSession(connection);
        System.out.println("Connected to websocket");

    }

    @Override
    public void onClose(WebSocketConnection connection) throws Exception {
        super.onClose(connection);
        System.out.println("disconnected");

    }

    @Override
    public void onMessage(WebSocketConnection connection, String msg) throws Throwable {
        super.onMessage(connection, msg);
        final Packet packet = Serializer.deserialize(msg);

        for(Command command : AutoPilot.INSTANCE.getWebSocketCommandManger().getCommands()) {
            if(command.getPacketName().equalsIgnoreCase(packet.getName()))
                command.execute(packet);
        }
    }

//    /**
//     *
//     * when our react-app connects to our a websocket we set our session.
//     *
//     * @param session - our webhook session
//     * @throws IOException - throws if connection is not handled properly
//     */
//    @OnWebSocketConnect
//    public void handleConnection(Session session) throws IOException {
//        AutoPilot.INSTANCE.getWebSocket().setSession(session);
//        System.out.println("Connected to websocket");
//    }
//
//    /**
//     *
//     * when our react-app disconnects from our websocket we close the java app.
//     *
//     * @param session - our webhook session
//     * @param statusCode - the status code given
//     * @param reason - the reason our client disconnected
//     */
//    @OnWebSocketClose
//    public void handleDisconnect(Session session, int statusCode, String reason) {
//        System.out.println("disconnected");
//      // System.exit(-1);
//    }
//
//    /**
//     *
//     * when our react-app sends a message we deserialize the input JSON to a {@link Packet} object
//     *
//     * @param session - our webhook session
//     * @param message - packet data sent through our websocket
//     * @throws IOException - thrown if packet serialization has failed
//     */
//    @OnWebSocketMessage
//    public void handlePacketMessage(Session session, String message) throws IOException {
//        final Packet packet = Serializer.deserialize(message);
//
//        for(Command command : AutoPilot.INSTANCE.getWebSocketCommandManger().getCommands()) {
//            if(command.getPacketName().equalsIgnoreCase(packet.getName()))
//                command.execute(packet);
//        }
//    }

}
