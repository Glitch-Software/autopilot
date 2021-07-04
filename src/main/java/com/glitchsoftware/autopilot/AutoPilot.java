package com.glitchsoftware.autopilot;

import club.minnced.discord.webhook.WebhookClient;
import com.glitchsoftware.autopilot.app.WebSocket;
import com.glitchsoftware.autopilot.app.command.WebSocketCommandManger;
import com.glitchsoftware.autopilot.app.packet.WebSocketPacketManager;
import com.glitchsoftware.autopilot.app.packet.impl.ClosingPacket;
import com.glitchsoftware.autopilot.bot.BotManager;
import com.glitchsoftware.autopilot.config.Config;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.command.SocketCommandManager;
import com.glitchsoftware.autopilot.socket.listener.SocketListener;
import com.glitchsoftware.autopilot.socket.model.User;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.SocketPacketManager;
import com.glitchsoftware.autopilot.socket.packet.impl.HandshakePacket;
import com.glitchsoftware.autopilot.task.TaskManager;
import com.glitchsoftware.autopilot.util.ClasspathUtils;
import com.glitchsoftware.autopilot.util.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.sikuli.script.ImagePath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


/**
 * @author Brennan
 * @since 5/24/2021
 **/
@Getter
public enum AutoPilot {
    INSTANCE;

    private final String VERSION = "1.0.6";

    private final File baseFile = new File(System.getenv("APPDATA"), "Glitch-Software" + File.separator + "AutoPilot");
    private final File botsFile = new File(baseFile, "bots");
    private final File imagesFile = new File(baseFile, "images");

    private final WebSocket webSocket = new WebSocket();
    private SocketConnection socketConnection;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Gson GSON = new GsonBuilder().create();

    private BotManager botManager;
    private TaskManager taskManager;

    private final WebSocketPacketManager webSocketPacketManager = new WebSocketPacketManager();
    private final WebSocketCommandManger webSocketCommandManger = new WebSocketCommandManger();

    private final SocketPacketManager socketPacketManager = new SocketPacketManager();
    private final SocketCommandManager socketCommandManager = new SocketCommandManager();

    @Setter
    private WebhookClient discordWebhook;

    private final Config config = new Config();

    @Setter
    private User currentUser = null;

    @Setter
    private JsonArray profitableItems;

    @Setter
    private Date lastPing;

    public void start() {
        if(!baseFile.exists())
            baseFile.mkdirs();
        if(!botsFile.exists())
            botsFile.mkdirs();
        setupImages();

        config.load();
        setupDiscordRPC();
        this.botManager = new BotManager();
        this.taskManager = new TaskManager();
        startSocket();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> getWebSocket().send(new ClosingPacket())));
    }

    private void setupImages() {
        if(imagesFile.exists()) {
            imagesFile.delete();
        }

        try {
            ClasspathUtils.extractResources("/images/", baseFile.toPath(), AutoPilot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        config.save();
    }

    private void startSocket() {
        this.socketConnection = new SocketConnection("167.71.89.120", 1337);

        this.socketConnection.setSocketListener(new SocketListener() {
            @Override
            public void onConnect(SocketConnection client) {
                client.send(new HandshakePacket());
            }

            @Override
            public void onReceivePacket(Packet packet, SocketConnection client) {
                for(Command command : getSocketCommandManager().getCommands()) {
                    if(command.getPacketName().equalsIgnoreCase(packet.getName()))
                        command.execute(packet, client);
                }
            }

            @Override
            public void onDisconnect(SocketConnection client) {
                getWebSocket().send(new ClosingPacket());
                System.exit(-1);
            }
        });

        new Thread(socketConnection).start();
    }

    public void shutdownRPC() {
        Logger.logInfo("[RPC] - Disconnected");
        DiscordRPC.discordShutdown();
    }

    public void setupDiscordRPC() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            Logger.logInfo("[RPC] - Setup with " + user.username + "#" + user.discriminator);
        }).build();
        DiscordRPC.discordInitialize("842570241464598539", handlers, true);

        DiscordRichPresence rich = new DiscordRichPresence
                .Builder("Monitoring....")
                .setDetails("v" + getVERSION())
                .setStartTimestamps(new Date().getTime())
                .setBigImage("logo", "")
                .build();
        DiscordRPC.discordUpdatePresence(rich);
    }

}
