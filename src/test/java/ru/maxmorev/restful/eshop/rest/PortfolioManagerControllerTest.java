package ru.maxmorev.restful.eshop.rest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@RunWith(SpringRunner.class)
public class PortfolioManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void create() {
        mockMvc.perform(
                post("/rest/api/private/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("/requests/portfolio-create.json"))
                        .with(user("some-manager@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "ADMIN"))
        )
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
    public void update() {
        mockMvc.perform(
                put("/rest/api/private/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("/requests/portfolio-update.json"))
                        .with(user("some-manager@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "ADMIN"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id", is(100)))
                .andExpect(jsonPath("$.data.name", is("name2")))
                .andExpect(jsonPath("$.data.description", is("update description")))
                .andExpect(jsonPath("$.data.shortDescription", is("quarkus is so cool")))
                .andExpect(jsonPath("$.data.images").isArray())
                .andExpect(jsonPath("$.data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/801578113711333.602d50622d83f.jpg")))
        ;
    }
}
