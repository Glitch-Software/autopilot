package com.glitchsoftware.autopilot.bot.types;

import com.glitchsoftware.autopilot.bot.AbstractBot;
import com.glitchsoftware.autopilot.task.Task;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * Our basic bot this bot that only needs a run
 **/
public abstract class BasicBot extends AbstractBot {

    public abstract boolean runBot(String site, Task task);

}
