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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@RunWith(SpringRunner.class)
public class PortfolioPublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void portfolios() {
        mockMvc.perform(
                get("/rest/api/public/portfolios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id", is(100)))
                .andExpect(jsonPath("$.data[0].name", is("Portfolio one")))
                .andExpect(jsonPath("$.data[0].description", is("Quarkus is so cool")))
                .andExpect(jsonPath("$.data[0].shortDescription", is("quarkus is so cool and ingeniously simple")))
                .andExpect(jsonPath("$.data[0].images").isArray())
                .andExpect(jsonPath("$.data[0].images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cf5ba0113711835.602d51d41b6e6.jpg")))
        ;
    }

    @Test
    @SneakyThrows
    public void portfolioById() {
        mockMvc.perform(
                get("/rest/api/public/portfolios/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id", is(100)))
                .andExpect(jsonPath("$.data.name", is("Portfolio one")))
                .andExpect(jsonPath("$.data.description", is("Quarkus is so cool")))
                .andExpect(jsonPath("$.data.shortDescription", is("quarkus is so cool and ingeniously simple")))
                .andExpect(jsonPath("$.data.images").isArray())
                .andExpect(jsonPath("$.data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cf5ba0113711835.602d51d41b6e6.jpg")))
        ;
    }

    @Test
    @SneakyThrows
    public void portfolioByWrongId() {
        mockMvc.perform(
                get("/rest/api/public/portfolios/107")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("fail")))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

}
