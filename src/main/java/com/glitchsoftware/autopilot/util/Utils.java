package com.glitchsoftware.autopilot.util;

import com.glitchsoftware.autopilot.AutoPilot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class Utils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);

    /**
     * kinda stupid but its to long for socket to send
     * because java NIO if the string is to big it will send in splits
     * cool idea but not for us sadly ;(
     */
    public static void setProfitableItems() {
        try {
            final Request request = new Request.Builder()
                    .url("http://127.0.0.1/api/profitable_items/")
                    .get()
                    .build();

            try(Response response = new OkHttpClient().newCall(request).execute()) {
                if(response.code() == 200) {
                    final JsonArray profitableItems = JsonParser.parseString(response.body().string()).getAsJsonArray();

                    JsonArray newArray = new JsonArray();
                    for(JsonElement jsonElement : profitableItems) {
                        JsonObject beforeObject = jsonElement.getAsJsonObject();

                        if(beforeObject.has("date_added")) {
                            final String dateAdded = beforeObject.get("date_added").getAsString();

                            Date firstDate = sdf.parse(dateAdded);
                            Date secondDate = sdf.parse(sdf.format(new Date()));

                            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                            beforeObject.addProperty("added", diff);
                        }

                        newArray.add(beforeObject);
                    }

                    AutoPilot.INSTANCE.setProfitableItems(newArray);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
