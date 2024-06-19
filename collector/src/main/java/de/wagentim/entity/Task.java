package de.wagentim.collector.entity;

import de.wagentim.collector.constants.IStringConstants;
import de.wagentim.collector.utils.IConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Task {
    private String taskName = IStringConstants.EMPTY_STRING;
    private String clazzName = IStringConstants.EMPTY_STRING;
    private int crawler;
    private boolean headless;
    private boolean trace;

}
