package com.glitchsoftware.autopilot.bot.types.rest.types;

import com.glitchsoftware.autopilot.bot.types.rest.RestBot;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;

/**
 * @author Brennan
 * @since 5/27/2021
 *
 * Our ConnectionBot is used for bots that need sessions example {@link com.glitchsoftware.autopilot.bot.impl.rest.KylinBot}
 **/
@Getter
public abstract class ConnectionBot extends RestBot {
    /**
     * Our sessionID that we store from bot connections.
     */
    @Setter
    private String sessionID;

    /**
     * If our bot session ID expires
     */
    @Setter
    private long expires;

    public abstract void onLoad();

    @Override
    public JsonObject toJSON() {
        final JsonObject jsonObject = super.toJSON();

        jsonObject.addProperty("session_id", getSessionID());

        if(expires != 0)
            jsonObject.addProperty("expires", getExpires());

        return jsonObject;
    }

    @Override
    public void fromJSON(JsonObject jsonObject) {
        super.fromJSON(jsonObject);

        if(jsonObject.has("session_id"))
            setSessionID(jsonObject.get("session_id").getAsString());

        if(jsonObject.has("expires"))
            setExpires(jsonObject.get("expires").getAsLong());
    }
}
