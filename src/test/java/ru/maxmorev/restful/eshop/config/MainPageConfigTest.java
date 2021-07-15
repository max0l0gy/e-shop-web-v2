package ru.maxmorev.restful.eshop.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MainPageConfigTest {
    @Autowired
    public MainPageConfig mainPageConfig;

    @Test
    public void load() {
        assertNotNull(mainPageConfig);
        assertEquals(2, mainPageConfig.newItemsAmount);
    }
}
