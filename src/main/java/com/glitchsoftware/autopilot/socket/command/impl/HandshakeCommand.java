package com.glitchsoftware.autopilot.socket.command.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.socket.SocketConnection;
import com.glitchsoftware.autopilot.socket.command.Command;
import com.glitchsoftware.autopilot.socket.packet.Packet;
import com.glitchsoftware.autopilot.socket.packet.impl.AuthPacket;
import com.glitchsoftware.autopilot.socket.packet.impl.HandshakePacket;
import com.glitchsoftware.autopilot.util.Utils;
import mmarquee.automation.UIAutomation;
import mmarquee.automation.controls.Application;
import mmarquee.automation.controls.ElementBuilder;

import javax.swing.*;
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

        if(!AutoPilot.INSTANCE.getConfig().getAuth().getLicense().isEmpty())
            client.send(new AuthPacket(AutoPilot.INSTANCE.getConfig().getAuth().getLicense(), Utils.getHWID()));
    }

    private void startApplication() {
        try {
            UIAutomation automation = UIAutomation.getInstance();

            if(automation.findPane("glitch-autopilot") == null) {
                System.out.println("Application not found starting");
                final String userDir = System.getProperty("user.dir") + File.separator + "app" + File.separator;

                final File uiFile = new File(userDir, "Glitch.exe");

                final Application application =
                        new Application(
                                new ElementBuilder()
                                        .automation(automation)
                                        .applicationPath(uiFile.getAbsolutePath()));
                application.launchOrAttach();

                application.waitForInputIdle(Application.SHORT_TIMEOUT);
            }

            automation = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
