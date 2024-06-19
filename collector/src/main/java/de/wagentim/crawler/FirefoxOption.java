package de.wagentim.collector.crawler;

import de.wagentim.collector.entity.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FirefoxOption {
    private boolean headless;
    private boolean trace;

    public boolean isSame(Task task) {
        return (this.headless && task.isHeadless()) &&
                (this.trace && task.isTrace());
    }
}
