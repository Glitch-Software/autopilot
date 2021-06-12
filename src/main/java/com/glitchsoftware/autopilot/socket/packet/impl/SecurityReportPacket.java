package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import lombok.AllArgsConstructor;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
@PacketManifest("security_report")
@AllArgsConstructor
public class SecurityReportPacket extends Packet {

    private int level;

    private String check;

    private String reason;

}
