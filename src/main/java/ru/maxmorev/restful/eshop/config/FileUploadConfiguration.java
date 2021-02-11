package ru.maxmorev.restful.eshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * configuration takes values from environment variables
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "fileioservice")
public class FileUploadConfiguration {

    private String endpoint;
    private String accessKey;

}
