package com.glitchsoftware.autopilot;

import com.glitchsoftware.autopilot.app.WebSocket;
import com.glitchsoftware.autopilot.app.command.CommandManger;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.PacketManager;
import com.glitchsoftware.autopilot.app.packet.serializer.Serializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.websocket.api.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Brennan
 * @since 5/24/2021
 **/
@Getter
public enum AutoPilot {
    INSTANCE;

    private final WebSocket webSocket = new WebSocket();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Gson GSON = new GsonBuilder().create();

    private final PacketManager packetManager = new PacketManager();
    private final CommandManger commandManger = new CommandManger();

    public void start() {
        webSocket.start();
    }

}
