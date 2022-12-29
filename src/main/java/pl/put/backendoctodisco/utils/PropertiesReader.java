package pl.put.backendoctodisco.utils;

import pl.put.backendoctodisco.exceptions.PropertiesNotAvailableException;
import pl.put.backendoctodisco.exceptions.SomethingWentWrongException;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {

    private static String getEnvironment() throws PropertiesNotAvailableException {
        return read("project.properties", "application");
    }

    public static String read(String key) throws PropertiesNotAvailableException {
        return read(key, getEnvironment());
    }

    private static String read(String key, String file) throws PropertiesNotAvailableException {

        try (InputStream input = new FileInputStream("src/main/resources/"+file+".properties")) {

            Properties prop = new Properties();
            prop.load(input);

            return prop.getProperty(key);

        } catch (IOException ex) {
            throw new PropertiesNotAvailableException(key, file);
        }
    }

}
