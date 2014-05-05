package com.geotmt.dsp;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {
    private static PropertiesLoader instance = null;
    private static Properties p = new Properties();
    ;

    private PropertiesLoader() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("ip.properties");
        if (url == null) {
            url = Thread.currentThread().getContextClassLoader().getResource("ip.properties");
            if (url == null)
                throw new IllegalStateException("ip.properties");
        }
        try {
            p.load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException("Could not load ip.properties:" + e);
        }
    }

    public static PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    public static String getProperty(String key) {
        String s = p.getProperty(key);
        return s;
    }
}
