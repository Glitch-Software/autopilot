package com.glitchsoftware.autopilot.app.command.impl.items;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.items.ProfitableItemsPacket;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class NeedItemsPacket extends Command {

    public NeedItemsPacket() {
        super("need_items");
    }

    @Override
    public void execute(Packet packet) {
        AutoPilot.INSTANCE.getWebSocket().send(new ProfitableItemsPacket(AutoPilot.INSTANCE.getProfitableItems()));
    }
}
