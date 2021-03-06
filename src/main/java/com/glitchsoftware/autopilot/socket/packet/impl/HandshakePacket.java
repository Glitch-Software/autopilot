package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@PacketManifest("handshake")
@NoArgsConstructor
@Getter
public class HandshakePacket extends Packet {

    @SerializedName("version")
    private String version;

}
