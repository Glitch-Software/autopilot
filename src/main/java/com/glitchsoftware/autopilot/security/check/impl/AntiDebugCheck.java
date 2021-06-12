package com.glitchsoftware.autopilot.security.check.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.ClosingPacket;
import com.glitchsoftware.autopilot.security.check.Check;
import com.glitchsoftware.autopilot.socket.packet.impl.SecurityReportPacket;

import java.lang.management.ManagementFactory;

/**
 * @author Brennan
 * @since 6/12/2021
 **/
public class AntiDebugCheck extends Check {
    private final String[] BLACKLISTED_ARGS = new String[] {
            "-javaagent",
            "-Xdebug",
            "-agentlib",
            "-Xrunjdwp",
            "-Xnoagent",
            "-verbose",
            "-DproxySet",
            "-DproxyHost",
            "-DproxyPort",
            "-Djavax.net.ssl.trustStore",
            "-Djavax.net.ssl.trustStorePassword"
    };

    public AntiDebugCheck() {
        super(0);
    }

    @Override
    public void run() {
        for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            for(String type : BLACKLISTED_ARGS) {
                if(arg.contains(type)) {
                    AutoPilot.INSTANCE.getWebSocket().send(new ClosingPacket());

                    AutoPilot.INSTANCE.getSocketConnection().send(new SecurityReportPacket(0, "debug_args_check",
                            "Blacklisted Argument: " + type));

                    System.exit(-1);
                }
            }
        }
    }
}
