package com.glitchsoftware.autopilot.bot.types.rest;

import com.glitchsoftware.autopilot.bot.AbstractBot;
import com.glitchsoftware.autopilot.bot.annotations.RestManifest;
import lombok.Getter;
import okhttp3.OkHttpClient;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * Our RestBot is used for bots that have a API example {Balko}
 **/
@Getter
public abstract class RestBot extends AbstractBot {
    /**
     * rest API link
     */
    private String apiUrl;

    /**
     * Our rest bots will need a http client
     */
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public RestBot() {
        if(getClass().isAnnotationPresent(RestManifest.class)) {
            this.apiUrl = getClass().getAnnotation(RestManifest.class).value();
        }
    }

    public abstract boolean runBot(String site, String sku, int taskQuantity);
}
