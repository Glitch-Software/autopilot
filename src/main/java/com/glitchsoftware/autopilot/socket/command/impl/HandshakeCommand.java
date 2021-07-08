package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.HandshakePacket;
import com.glitchsoftware.autopilot.util.Ping;
import com.glitchsoftware.autopilot.util.Utils;
import org.sikuli.script.App;

import java.io.File;


/**
 * @author Brennan
 * @since 5/28/2021
 **/
public class HandshakeCommand extends Command {

    public HandshakeCommand() {
        super("handshake");
    }

    @Override
    public void execute(Packet packet, SocketConnection client) {
        final HandshakePacket handshakePacket = (HandshakePacket) packet;

//        if(!AutoPilot.INSTANCE.getVERSION().equals(handshakePacket.getVersion())) {
//            JOptionPane.showMessageDialog(null, "Your version is outdated! Please update to " + handshakePacket.getVersion(), "Please Update", JOptionPane.INFORMATION_MESSAGE);
//            System.exit(-1);
//            return;
//        }

        startApplication();

        AutoPilot.INSTANCE.getWebSocket().start();
        Ping.checkPing();

        if(!AutoPilot.INSTANCE.getConfig().getAuth().getLicense().isEmpty())
            client.send(new AuthPacket(AutoPilot.INSTANCE.getConfig().getAuth().getLicense(), Utils.getHWID()));
    }

    private void startApplication() {
        try {
            final String userDir = System.getProperty("user.dir") + File.separator + "app" + File.separator;
            final File uiFile = new File(userDir, "Glitch.exe");

            App botApp = findApp("glitch-autopilot");
            if(botApp == null) {
                botApp = App.open(uiFile.getAbsolutePath());
                botApp.waitForWindow();
            }
            botApp.focus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private App findApp(String name) {
        for(App app : App.getApps()) {
            if(app.getName().toLowerCase().contains(name.toLowerCase())) {
                return app;
            }
        }

        return null;
    }

}
