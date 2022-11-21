package pl.put.backendoctodisco;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "flashcard")
        public class ConfigProperties {

    private String fileFolderUrl;
}
