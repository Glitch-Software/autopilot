package com.glitchsoftware.autopilot.socket;

import com.glitchsoftware.autopilot.socket.listener.SocketListener;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.serializer.Serializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@Getter
@Setter
public class SocketConnection implements Runnable {
    private AsynchronousSocketChannel client;

    private final String address;
    private final int port;

    private SocketListener socketListener;

    public SocketConnection(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        reconnect:
        while (true) {
            try {
                if(client == null) {
                    this.client = AsynchronousSocketChannel.open();
                    final Future<Void> result = client.connect(new InetSocketAddress(address, port));
                    result.get();

                    socketListener.onConnect(this);
                }
            } catch (IOException e) {
                e.printStackTrace();

                this.delayReconnection();
                continue reconnect;
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage().contains("he remote computer refused the network connection.")) {
                    System.exit(-1);
                }
                e.printStackTrace();
            }

            while (client.isOpen()) {
                try {
                    final ByteBuffer buffer = ByteBuffer.allocate(1024);
                    final Future<Integer> read = client.read(buffer);
                    read.get();

                    final String string = new String(buffer.array()).trim();

                    System.out.println(string);
                    JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();

                    socketListener.onReceivePacket(Serializer.deserialize(jsonObject), this);

                    buffer.clear();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    if(e.getMessage().contains("An existing connection was forcibly closed by the remote host")) {
                        socketListener.onDisconnect(this);
                    }
                }

                this.delayReconnection();
                continue reconnect;
            }

            this.delayReconnection();
            continue reconnect; //Retry connection
        }
    }

    public void send(Packet input) {
        System.out.println("sending? " + input.getName());
        final ByteBuffer buffer = ByteBuffer.wrap(Serializer.serialize(input).getBytes());;

        client.write(buffer);
        buffer.flip();
    }

    private void delayReconnection() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
