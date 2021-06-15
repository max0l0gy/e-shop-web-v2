package ru.maxmorev.restful.eshop.feignclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
public class EuropeanCentralBankCurrencyRateApiTest {
    @Autowired
    EuropeanCentralBankCurrencyRateApi europeanCentralBankCurrencyRateApi;

    @Test
    public void loadContext() {
        assertNotNull(europeanCentralBankCurrencyRateApi);
    }

    @Test
    public void getCurrenciesRateXml() {
        String responseXml = europeanCentralBankCurrencyRateApi.getCurrenciesRateXml();
        log.info("Response: {}", responseXml);
        assertEquals(readFileToString("__files/responses/eurofxref-daily.xml"), responseXml);
    }
}
