package com.glitchsoftware.autopilot.security.check;

import lombok.Getter;

import java.util.TimerTask;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
@Getter
public abstract class Check extends TimerTask {
    private final long timeBetween;

    public Check(long timeBetween) {
        this.timeBetween = timeBetween;
    }
}
