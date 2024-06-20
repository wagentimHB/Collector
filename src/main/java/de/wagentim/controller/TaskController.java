package de.wagentim.collector.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.wagentim.collector.constants.IFileConstants;
import de.wagentim.collector.entity.TaskList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaskController {

    private Gson gson;
    private TaskList taskList;

    public TaskController() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadTasks();
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void loadTasks() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream(IFileConstants.FILE_TASK);
        InputStreamReader inr = new InputStreamReader(resourceStream);
        taskList = gson.fromJson(inr, TaskList.class);
    }

    public void writeToFile(TaskList taskList) {
        try {
            gson.toJson(taskList, new FileWriter(IFileConstants.FILE_TASK));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String write2String(TaskList taskList) {
        return gson.toJson(taskList);
    }
}
