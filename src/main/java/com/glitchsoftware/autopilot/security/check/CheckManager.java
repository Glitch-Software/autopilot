package com.glitchsoftware.autopilot.security.check;

import com.glitchsoftware.autopilot.security.check.impl.AntiDebugCheck;
import com.glitchsoftware.autopilot.security.check.impl.ProcessWalkerCheck;

import java.util.Timer;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class CheckManager {

    private final Timer timer = new Timer();

    public CheckManager() {
        this.timer.schedule(new AntiDebugCheck(), 0); // #TODO comment out when using intelij
        this.executeCheck(new ProcessWalkerCheck());
    }

    private void executeCheck(Check check) {
        this.timer.schedule(check, 0, check.getTimeBetween() * 1000);
    }
}
