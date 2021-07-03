package com.glitchsoftware.autopilot.security.check.impl;

import com.glitchsoftware.autopilot.AutoPilot;
import com.glitchsoftware.autopilot.app.packet.impl.ClosingPacket;
import com.glitchsoftware.autopilot.security.check.Check;
import com.glitchsoftware.autopilot.socket.packet.impl.SecurityReportPacket;
import com.glitchsoftware.autopilot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class ProcessWalkerCheck extends Check {

    private final Map<Level, String> BLACKLISTED_PROCESS = new HashMap<Level, String>() {{
        put(new Level(1), "ollydbg.exe");
        put(new Level(1), "ProcessHacker.exe");
        put(new Level(1), "tcpview.exe");
        put(new Level(1), "autoruns.exe");
        put(new Level(1), "autorunsc.exe");
        put(new Level(1), "filemon.exe");
        put(new Level(1), "procmon.exe");
        put(new Level(1), "regmon.exe");
        put(new Level(1), "procexp.exe");
        put(new Level(0), "idaq.exe");
        put(new Level(0), "idaq64.exe");
        put(new Level(0), "ImmunityDebugger.exe");
        put(new Level(0), "Wireshark.exe");
        put(new Level(0), "dumpcap.exe");
        put(new Level(0), "HookExplorer.exe");
        put(new Level(0), "ImportREC.exe");
        put(new Level(0), "PETools.exe");
        put(new Level(0), "LordPE.exe");
        put(new Level(0), "dumpcap.exe");
        put(new Level(1), "SysInspector.exe");
        put(new Level(1), "proc_analyzer.exe");
        put(new Level(0), "sniff_hit.exe");
        put(new Level(1), "sysAnalyzer.exe");
        put(new Level(0), "windbg.exe");
        put(new Level(1), "joeboxcontrol.exe");
        put(new Level(1), "joeboxserver.exe");
        put(new Level(0), "fiddler.exe");
        put(new Level(0), "Charles.exe");
        put(new Level(0), "HTTPDebuggerUI.exe");
        put(new Level(0), "Fiddler Everywhere.exe");
        put(new Level(0), "HTTPDebuggerSvc.exe");
        put(new Level(0), "Fiddler.WebUi.exe");
        put(new Level(0), "Proxyman");
    }};


    public ProcessWalkerCheck() {
        super(1);
    }

    @Override
    public void run() {
        for(Map.Entry<Level, String> process : BLACKLISTED_PROCESS.entrySet()) {
                if(Utils.isProcessRunning(process.getValue())) {
                    AutoPilot.INSTANCE.getWebSocket().send(new ClosingPacket());

                    AutoPilot.INSTANCE.getSocketConnection().send(new SecurityReportPacket(process.getKey().getLevel(), "process_check",
                            "Blacklisted Process: " + process.getValue()));

                    System.exit(-1);
                }
        }
    }

    @Getter
    @AllArgsConstructor
    private static class Level {
        private final int level;
    }
}
