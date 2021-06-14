package com.glitchsoftware.autopilot.security;

import com.glitchsoftware.autopilot.security.check.CheckManager;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class Security {

    public static void start() {
        new CheckManager();
    }
}
