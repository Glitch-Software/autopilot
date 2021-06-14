package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.items.RemoveItemPacket;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.RemoveProfitableItem;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
public class RemoveProfitableItemCommand extends Command {

    public RemoveProfitableItemCommand() {
        super("remove_profitableitem");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final RemoveProfitableItem removeProfitableItem = (RemoveProfitableItem) packet;

        AutoPilot.INSTANCE.getWebSocket().send(new RemoveItemPacket(removeProfitableItem.getSku()));
    }
}
