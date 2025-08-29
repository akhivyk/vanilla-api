package com.solvd.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FileReader {
    public static String getConfigValue(String value) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));

            return properties.getProperty(value);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getQueryFromFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
