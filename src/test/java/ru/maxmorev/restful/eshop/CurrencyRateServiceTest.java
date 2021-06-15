package ru.maxmorev.restful.eshop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.feignclient.domain.ecb.CurrencyRateContainer;
import ru.maxmorev.restful.eshop.services.CurrencyRateService;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
public class CurrencyRateServiceTest {

    @Autowired
    CurrencyRateService currencyRateService;

    @Test
    public void getCurrencyRateContainer() {
        CurrencyRateContainer rateContainer = currencyRateService.getCurrencyRateContainer();
        Assertions.assertEquals(32, rateContainer.getRates().size());
        Assertions.assertEquals("Reference rates", rateContainer.getSubject());
        Assertions.assertEquals("European Central Bank", rateContainer.getSource());
    }

    @Test
    public void testCache() {
        CurrencyRateContainer firstRateContainer = currencyRateService.getCurrencyRateContainer();
        for (int i = 0; i < 3; i++) {
            CurrencyRateContainer rateContainer = currencyRateService.getCurrencyRateContainer();
            Assertions.assertEquals(firstRateContainer.getCurrentTimestamp(), rateContainer.getCurrentTimestamp());
        }
    }

}
