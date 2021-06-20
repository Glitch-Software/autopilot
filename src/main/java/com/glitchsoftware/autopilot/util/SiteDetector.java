package com.glitchsoftware.autopilot.util;

import lombok.Getter;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
@Getter
public enum SiteDetector {
    FOOTLOCKER_US("Footlocker US", "footlocker.com", "footlockerus"),
    FOOTLOCKER_CA("Footlocker CA", "footlocker.ca", "footlockerca"),
    KIDS_FOOTLOCKER("Kids Footlocker", "kidsfootlocker.com", "kidsfootlocker"),
    FOOTACTION("Footaction", "footaction.com", "footaction"),
    CHAMPS("Champs Sports", "champssports.com", "champssports"),
    EASYBAY("Eastbay", "eastbay.com", "eastbay");

    private String name, url, group;

    SiteDetector(String name, String url, String group) {
        this.name = name;
        this.url = url;
        this.group = group;
    }

    public static String getGroup(String site) {
        for(SiteDetector siteDetector : values()) {
            if(siteDetector.getUrl().equalsIgnoreCase(site))
                return siteDetector.getGroup();
        }

        return null;
    }

    public static String getURL(String name) {
        for(SiteDetector siteDetector : values()) {
            if(siteDetector.getName().equalsIgnoreCase(name))
                return siteDetector.getUrl();
        }

        return null;
    }
}
