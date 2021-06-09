package com.glitchsoftware.autopilot.socket.packet.impl;

import com.glitchsoftware.autopilot.socket.model.ProfitableItem;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.PacketManifest;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Brennan
 * @since 6/7/2021
 **/
@PacketManifest("profitable_item")
@Getter
@NoArgsConstructor
public class ProfitableItemPacket extends Packet {

    private ProfitableItem profitableItem;



}
