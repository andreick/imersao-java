package com.andreick.util;

import java.io.InputStream;

public class Resources {

    public static InputStream getResourceAsStream(String name) {
        return Resources.class.getClassLoader().getResourceAsStream(name);
    }
}
