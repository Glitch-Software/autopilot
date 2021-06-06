package com.glitchsoftware.autopilot.task;

import com.glitchsoftware.autopilot.AutoPilot;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamdev.jxbrowser.js.Json;
import lombok.Getter;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Brennan
 * @since 6/4/2021
 **/
@Getter
public class TaskManager {
    private final List<Task> tasks = new LinkedList<>();

    final File file;

    public TaskManager() {
        this.file = new File(AutoPilot.INSTANCE.getBaseFile(),"tasks.json");

        loadTasks();
    }

    public JsonArray getAsJSON() {
        final JsonArray jsonArray = new JsonArray();

        for(Task task : getTasks()) {
            jsonArray.add(task.toJSON());
        }

        return jsonArray;
    }

    public Task getTask(String id) {
        for(Task task : getTasks()) {
            if(task.getId().equalsIgnoreCase(id))
                return task;
        }

        return null;
    }

    public void addTask(Task task) {
        this.tasks.add(task);

        save();
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);

        save();
    }

    public void loadTasks() {
        try {
            if(file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                final JsonArray jsonArray = JsonParser.parseString(bufferedReader.readLine()).getAsJsonArray();

                for(JsonElement jsonElement : jsonArray) {
                    if(jsonElement instanceof JsonObject) {
                        final Task task = new Task();
                        task.fromJSON((JsonObject) jsonElement);

                        getTasks().add(task);
                    }
                }

                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void save() {
        try {
            if(file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }

            final FileWriter fileWriter = new FileWriter(file);

            final JsonArray jsonArray = new JsonArray();

            for(Task task : getTasks()) {
                jsonArray.add(task.toJSON());
            }

            fileWriter.write(AutoPilot.INSTANCE.getGSON().toJson(jsonArray));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
