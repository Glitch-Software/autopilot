package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Brennan
 * @since 6/8/2021
 **/
@PacketManifest("error")
@NoArgsConstructor
@Getter
public class ErrorPacket extends Packet {

    @SerializedName("type")
    private String type;

    @SerializedName("message")
    private String message;

}
