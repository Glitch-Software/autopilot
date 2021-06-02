package com.glitchsoftware.autopilot.config.impl;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Brennan
 * @since 6/1/2021
 **/
@Getter
@Setter
public class Auth {

    @SerializedName("license")
    private String license = "";
}
