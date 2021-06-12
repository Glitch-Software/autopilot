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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Brennan
 * @since 5/24/2021
 **/
@Getter
public enum AutoPilot {
    INSTANCE;

    private final File baseFile = new File(System.getenv("APPDATA"), "Glitch-Software" + File.separator + "AutoPilot");
    private final File botsFile = new File(baseFile, "bots");

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

    public void start() {
        if(!baseFile.exists())
            baseFile.mkdirs();
        if(!botsFile.exists())
            botsFile.mkdirs();

        config.load();
        this.botManager = new BotManager();
        this.taskManager = new TaskManager();
        startSocket();

        if(config.isDiscordRPC())
            setupDiscordRPC();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> getWebSocket().send(new ClosingPacket())));
    }

    public void save() {
        config.save();
    }

    private void startSocket() {
        //167.71.89.120
        this.socketConnection = new SocketConnection("127.0.0.1", 1337);

        this.socketConnection.setSocketListener(new SocketListener() {
            @Override
            public void onConnect(SocketConnection client) {
                client.send(new HandshakePacket());
            }

            @Override
            public void onReceivePacket(Packet packet, SocketConnection client) {
                System.out.println(packet.getName());
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

    private void setupDiscordRPC() {
        final IPCClient client = new IPCClient(842570241464598539L);

        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                final RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("Monitoring " + new String(Character.toChars(0x1F45F)) + "...")
                        .setDetails("1.0.0")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("logo", "");
                client.sendRichPresence(builder.build());
            }
        });

        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            e.printStackTrace();
        }
    }

}
