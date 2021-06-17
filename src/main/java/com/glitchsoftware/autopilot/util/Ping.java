package com.glitchsoftware.autopilot.util;

import com.glitchsoftware.autopilot.AutoPilot;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Brennan
 * @since 6/16/2021
 **/
public class Ping {

    private static final Timer timer = new Timer();

    public static void checkPing() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(AutoPilot.INSTANCE.getLastPing() != null) {
                    long elapsedTimeInSeconds = secondsBetween(AutoPilot.INSTANCE.getLastPing(), new Date());

                    if(elapsedTimeInSeconds > 10)
                        System.exit(-1);
                }
            }
        }, 0, 500);
    }

    private static Long secondsBetween(Date first, Date second){
        return (second.getTime() - first.getTime())/1000;
    }

}
