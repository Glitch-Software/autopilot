package com.glitchsoftware.autopilot.app.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Brennan
 * @since 5/25/2021
 *
 * Our user model made for our WebSocket
 **/
@Getter
public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
