package ru.maxmorev.restful.eshop.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@Profile({"dev","prod"})
@RequiredArgsConstructor
public class Startup {
    private final DefaultManager defaultManager;


    @PostConstruct
    public void createDefaultManager() {
        log.info("startup ::: createDefaultManager");
        defaultManager.createDefaultManager();
    }
}
