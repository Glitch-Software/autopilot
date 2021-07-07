package com.glitchsoftware.autopilot.util;

import com.glitchsoftware.autopilot.AutoPilot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Brennan
 * @since 6/5/2021
 **/
public class Utils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void enter() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void deleteAll() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
    }

    /**
     * kinda stupid but its to long for socket to send
     * because java NIO if the string is to big it will send in splits
     * cool idea but not for us sadly ;(
     */
    public static void setProfitableItems() {
        try {
            final Request request = new Request.Builder()
                    .url("http://167.71.89.120:8080/api/profitable_items/")
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

    public static String getHWID() {
        final String base = System.getProperty("user.name") + System.getProperty("os.version") + System.getProperty("os.name") + System.getProperty("os.arch");
        final StringBuilder buffer = new StringBuilder();
        int intValue;
        for(int x = 0; x < base.length(); x++) {
            int cursor = 0;
            intValue = base.charAt(x);
            final String binaryChar = Integer.toBinaryString(base.charAt(x));
            for(int i = 0; i < binaryChar.length(); i++) {
                if(binaryChar.charAt(i) == '1')
                    cursor += 1;
            }
            if((cursor % 2) > 0)
                intValue += 128;
            buffer.append(Integer.toHexString(intValue));
        }

        return buffer.toString();
    }


    public static boolean isProcessRunning(String processName) {
        try {
            final Process proc = Runtime.getRuntime().exec("wmic.exe");
            final BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            final OutputStreamWriter oStream = new OutputStreamWriter(proc.getOutputStream());
            oStream.write(String.format("process where name='%s'", processName));
            oStream.flush();
            oStream.close();

            String line;
            final StringBuilder builder = new StringBuilder();
            while ((line = input.readLine()) != null) {
                builder.append(line);
            }
            input.close();
            oStream.close();
            proc.destroy();

            return builder.toString().contains(processName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return false;
    }
}
