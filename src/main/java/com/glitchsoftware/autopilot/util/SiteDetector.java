package com.glitchsoftware.autopilot.util;

import lombok.Getter;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@Getter
public enum SiteDetector {
    FOOTLOCKER_US("Footlocker US", "footlocker.com"),
    FOOTLOCKER_CA("Footlocker CA", "footlocker.ca"),
    KIDS_FOOTLOCKER("Kids Footlocker", "kidsfootlocker.com"),
    FOOTACTION("Footaction", "kidsfootlocker.com"),
    CHAMPS("Champs Sports", "champssports.com"),
    EASYBAY("Eastbay", "eastbay.com");

    private String name, url;

    SiteDetector(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static String getURL(String name) {
        for(SiteDetector siteDetector : values()) {
            if(siteDetector.getName().equalsIgnoreCase(name))
                return siteDetector.getUrl();
        }

        return null;
    }
}
