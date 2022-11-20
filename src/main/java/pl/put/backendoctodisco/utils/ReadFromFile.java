package pl.put.backendoctodisco.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.put.backendoctodisco.ConfigProperties;

@Component
public class ReadFromFile {

    private final ConfigProperties configProperties;

    @Autowired
    public ReadFromFile(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }



}
