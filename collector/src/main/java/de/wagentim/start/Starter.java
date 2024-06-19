package de.wagentim.collector.start;

import de.wagentim.collector.controller.MainController;
import de.wagentim.collector.controller.SettingController;
import de.wagentim.collector.entity.Task;
import de.wagentim.collector.entity.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class.getName());
    private SettingController settingController;
    private TaskList taskList;
    public void run() {
        logger.info("Start to loading setting file and read all global settings...");
        readSettings();
        readTasks();
        if(taskList.isEmpty()) {
            logger.error("No Task read from task files");
            return;
        }
        for (Task t : taskList.getTaskList()
             ) {
            MainController.INSTANCE().execute(t);
        }
    }

    private void readTasks() {
        taskList = MainController.INSTANCE().getTaskController().getTaskList();
    }

    private void readSettings() {
        settingController = MainController.INSTANCE().getSettingController();
    }

    public static void main(String[] args) {
        Starter s = new Starter();
        s.run();
    }
}
