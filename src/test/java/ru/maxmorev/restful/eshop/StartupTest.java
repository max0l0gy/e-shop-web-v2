package ru.maxmorev.restful.eshop;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;
import ru.maxmorev.restful.eshop.startup.DefaultManager;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Deafult Manager test")
@AutoConfigureWireMock(port = 4555)
class StartupTest {

    @Autowired
    DefaultManager defaultManager;

    @BeforeEach
    public void cleanStubs() {
        WireMock.reset();
    }

    @Test
    public void startupTestDefaultManagerNotExist() {
        stubFor(post(urlEqualTo("/admin/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("EshopCustomerApi/findManagerByDefaultEmail.json")));
        stubFor(post(urlEqualTo("/send/template/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("mailSend.ok.json")));
        Optional<CustomerDto> co = defaultManager.createDefaultManager();
        assertTrue(co.isPresent());
    }

    @Test
    public void startupTestDefaultManagerExist() {
        stubFor(post(urlEqualTo("/admin/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("EshopCustomerApi/findManagerByDefaultEmail.json")));
        stubFor(get(urlEqualTo("/customer/email/manager@manager.test"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("EshopCustomerApi/findManagerByDefaultEmail.json")));
        Optional<CustomerDto> co = defaultManager.createDefaultManager();
        assertTrue(co.isEmpty());
    }

}
