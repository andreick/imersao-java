package com.andreick.api.imdb;

import com.andreick.util.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImdbProperties {

    private static final String PROPERTIES_NAME = "imdb-api.properties";

    private final Properties properties;

    public ImdbProperties() {
        this.properties = new Properties();
        InputStream stream = Resources.getResourceAsStream(PROPERTIES_NAME);
        try {
            this.properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getKey() {
        return properties.getProperty("key");
    }
}
