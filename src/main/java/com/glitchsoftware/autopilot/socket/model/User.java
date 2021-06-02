package com.glitchsoftware.autopilot.socket.model;

import lombok.Getter;

/**
 * @author Brennan
 * @since 5/30/2021
 **/
@Getter
public class User {
    private String license;

    private String username;

    private String discriminator;

    private String email;

    private String hwid;

    private boolean active;

    private boolean banned;
}
