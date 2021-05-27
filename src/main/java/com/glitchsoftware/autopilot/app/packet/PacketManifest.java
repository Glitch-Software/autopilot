package com.glitchsoftware.autopilot.app.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Brennan
 * @since 5/24/2021
 *
 * used to identify our packets name used for {@link com.glitchsoftware.autopilot.app.packet.serializer.Serializer}
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketManifest {

    /**
     * Our packets name
     */
    String value();

}
