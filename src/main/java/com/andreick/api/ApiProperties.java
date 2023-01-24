package com.andreick.api;

import com.andreick.util.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiProperties {

    private static final String PROPERTIES_NAME = "api.properties";

    private final Properties properties;

    public ApiProperties() {
        this.properties = new Properties();
        InputStream stream = Resources.getResourceAsStream(PROPERTIES_NAME);
        try {
            this.properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getKey(String name) {
        return properties.getProperty("key." + name);
    }
}
