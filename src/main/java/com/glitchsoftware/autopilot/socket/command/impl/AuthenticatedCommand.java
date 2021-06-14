package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.InitializedPacket;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthenticatedPacket;
import com.glitchsoftware.autopilot.util.Utils;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
public class AuthenticatedCommand extends Command {

    public AuthenticatedCommand() {
        super("authenticated");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final AuthenticatedPacket authenticatedPacket = (AuthenticatedPacket) packet;

        if(AutoPilot.INSTANCE.getCurrentUser() == null) {
            AutoPilot.INSTANCE.getConfig().getAuth().setLicense(authenticatedPacket.getUser().getLicense());
            AutoPilot.INSTANCE.getConfig().save();
            AutoPilot.INSTANCE.setCurrentUser(authenticatedPacket.getUser());

            Utils.setProfitableItems();

            if(!AutoPilot.INSTANCE.getWebSocket().isAuthed()) {
                AutoPilot.INSTANCE.getWebSocket().send(new InitializedPacket(AutoPilot.INSTANCE.getCurrentUser(),
                        AutoPilot.INSTANCE.getVERSION(),
                        AutoPilot.INSTANCE.getConfig().getWebHooks().getDiscordWebhook(),
                        AutoPilot.INSTANCE.getConfig().isDiscordRPC(),
                        AutoPilot.INSTANCE.getConfig().getDeleteTimeout(),
                        AutoPilot.INSTANCE.getBotManager().getBotsAsJSON(),
                        AutoPilot.INSTANCE.getTaskManager().getAsJSON()));
            }
        }
    }
}
