package ru.maxmorev.restful.eshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;

public class TestUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String readFileToString(String filePath) {
        return Resources.toString(Resources.getResource(filePath), Charsets.UTF_8);
    }
}
