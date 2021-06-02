package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.ProfitableItemsPacket;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
public class ProfitableItemsCommand extends Command {

    public ProfitableItemsCommand() {
        super("profitable_items");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final ProfitableItemsPacket profitableItemsPacket = (ProfitableItemsPacket) packet;
        System.out.println(profitableItemsPacket.getItemsElement());
    }
}
