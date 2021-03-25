package ru.maxmorev.restful.eshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String readFileToString(String filePath) {
        return FileUtils.readFileToString(
                new File(TestUtils.class.getResource(filePath).getPath()),
                StandardCharsets.UTF_8.name()
        );
    }
}
