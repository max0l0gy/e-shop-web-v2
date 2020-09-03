package ru.maxmorev.restful.eshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "manager")
public class ManagerConfig {
    String email;
    String password;
    String fullName;
    String country;
    String postcode;
    String city;
    String address;
}
