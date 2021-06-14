package com.glitchsoftware.autopilot.app.command.impl.settings;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.command.Command;
import com.glitchsoftware.autopilot.app.packet.Packet;
import com.glitchsoftware.autopilot.app.packet.impl.settings.UpdateRPCPacket;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
public class UpdateRPCCommand extends Command {

    public UpdateRPCCommand() {
        super("update_rpc");
    }

    @Override
    public void execute(Packet packet) {
        final UpdateRPCPacket rpcPacket = (UpdateRPCPacket) packet;

        System.out.println(rpcPacket.isRpc());
        AutoPilot.INSTANCE.getConfig().setDiscordRPC(rpcPacket.isRpc());
        AutoPilot.INSTANCE.getConfig().save();

        if(rpcPacket.isRpc()) {
            AutoPilot.INSTANCE.setupDiscordRPC();
        } else {
            AutoPilot.INSTANCE.shutdownRPC();
        }
    }
}
