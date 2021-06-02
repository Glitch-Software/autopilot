package com.glitchsoftware.autopilot.socket.listener;

import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.packet.Packet;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
public interface SocketListener {

    void onConnect(SocketConnection client);

    void onReceivePacket(Packet packet, SocketConnection client);

    void onDisconnect(SocketConnection client);

}
