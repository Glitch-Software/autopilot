package com.glitchsoftware.autopilot.socket.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Brennan
 * @since 5/28/2021
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketManifest {

    /**
     * Our packets name
     */
    String value();

}
