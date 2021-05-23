package ru.maxmorev.restful.eshop.rest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void generateResetPasswordCodeError() {
        mockMvc.perform(
                post("/rest/api/public/customer/reset-password-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("/requests/reset-password-error.json"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("error")))

        ;
    }

    @Test
    @SneakyThrows
    public void generateResetPasswordCodeSuccess() {
        mockMvc.perform(
                post("/rest/api/public/customer/reset-password-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("/requests/reset-password-success.json"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))

        ;
    }
}
