package de.wagentim.collector.controller;

import de.wagentim.collector.constants.IFileConstants;
import de.wagentim.collector.constants.ISettingConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingController {

    private final Properties properties;

    public SettingController() {
        properties = loadProperties();
    }
    public Properties loadProperties() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream(IFileConstants.FILE_SETTING);
        Properties props = new Properties();
        try {
            props.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    public boolean useHeadless() {
        return Boolean.parseBoolean(properties.getProperty(ISettingConstants.SETTING_HEADLESS));
    }

    public int getProgramMode() {
        return Integer.parseInt(properties.getProperty(ISettingConstants.SETTING_PROGRAM_MODE));
    }

    public int getDriverType() {
        return Integer.parseInt(properties.getProperty(ISettingConstants.SETTING_DRIVER_TYPE));
    }

    public String getFirefoxDriver() {
        return properties.getProperty(ISettingConstants.SETTING_DRIVER_TYPE_FIREFOX);
    }

    public String getFirefoxDriverLocation() {
        return properties.getProperty(ISettingConstants.SETTING_DRIVER_TYPE_FIREFOX_LOCATION);
    }
}
