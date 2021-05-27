package com.glitchsoftware.autopilot.bot.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * Used to identify our rest bot API link
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestManifest {

    String value();

}
