package ru.maxmorev.restful.eshop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.feignclient.domain.ecb.CurrencyRateContainer;
import ru.maxmorev.restful.eshop.mapper.EcbCurrencyRatesMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CurrencyRateContainerTest {
    @Autowired
    EcbCurrencyRatesMapper ecbCurrencyRatesMapper;

    @Test
    public void findByCurrency() {
        String xml = readFileToString("__files/responses/eurofxref-daily.xml");
        CurrencyRateContainer rateContainer = ecbCurrencyRatesMapper.parseXml(xml);
        Optional<CurrencyRateContainer.CurrencyRate> usd = rateContainer.findByCurrency("USD");
        assertTrue(usd.isPresent());
        assertEquals("USD", usd.get().getCurrency());
        assertEquals(0, usd.get().getRate().compareTo(BigDecimal.valueOf(1.2125d)));
    }

    @Test
    public void convertUsdToRub() {
        String xml = readFileToString("__files/responses/eurofxref-daily.xml");
        CurrencyRateContainer rateContainer = ecbCurrencyRatesMapper.parseXml(xml);
        Optional<BigDecimal> rub = rateContainer.convertUsdToRub(BigDecimal.valueOf(15.0d));
        log.info("Rub {}", rub.get());
        assertEquals(0, rub.get().compareTo(BigDecimal.valueOf(1304.43d)));
    }

}
