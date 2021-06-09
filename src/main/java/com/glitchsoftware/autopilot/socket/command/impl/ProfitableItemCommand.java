package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.items.NewProfitableItemPacket;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.model.ProfitableItem;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.ProfitableItemPacket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 6/7/2021
 **/
public class ProfitableItemCommand extends Command {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);

    public ProfitableItemCommand() {
        super("profitable_item");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final ProfitableItemPacket profitableItemPacket = (ProfitableItemPacket) packet;
        final ProfitableItem profitableItem = profitableItemPacket.getProfitableItem();

        final String dateAdded = profitableItem.getDate();

        try {
            Date firstDate = sdf.parse(dateAdded);
            Date secondDate = sdf.parse(sdf.format(new Date()));

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            AutoPilot.INSTANCE.getWebSocket().send(new NewProfitableItemPacket(diff, profitableItem.getItemName(),
                    profitableItem.getSku(), profitableItem.getStockXUrl(), profitableItem.getItemImage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
