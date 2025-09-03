package com.solvd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = FileReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getConfigValue(String key) {
        return properties.getProperty(key);
    }

    public static String getQueryFromFile(String path) {
        try {
            InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(path);
            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + path);
            }
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load query from file: " + path, e);
        }
    }
}
