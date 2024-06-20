package de.wagentim.collector.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class TaskList {
    public List<Task> taskList;

    public boolean isEmpty() {
        return taskList == null || taskList.isEmpty();
    }
}
