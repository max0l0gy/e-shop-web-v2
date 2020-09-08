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
    public String email;
    public String password;
    public String fullName;
    public String country;
    public String postcode;
    public String city;
    public String address;
}
