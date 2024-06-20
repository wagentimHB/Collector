package de.wagentim.collector.entity;

import de.wagentim.collector.utils.IConstants;

public class KeyValuePair
{
    private String key = IConstants.TXT_EMPTY_STRING;
    private String value = IConstants.TXT_EMPTY_STRING;

    public KeyValuePair()
    {
        this.key = IConstants.TXT_EMPTY_STRING;
        this.value = IConstants.TXT_EMPTY_STRING;
    }

    public KeyValuePair(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isComplete()
    {
        return (!this.key.isEmpty()) && (!this.value.isEmpty());
    }
}
