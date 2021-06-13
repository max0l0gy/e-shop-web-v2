package ru.maxmorev.restful.eshop.mapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.feignclient.domain.ecb.CurrencyRateContainer;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EcbCurrencyRatesMapperTest {
    @Autowired
    EcbCurrencyRatesMapper ecbCurrencyRatesMapper;

    @Test
    public void loadContext() {
        assertNotNull(ecbCurrencyRatesMapper);
    }

    @Test
    @SneakyThrows
    public void parseXml() {
        String xml = readFileToString("__files/responses/eurofxref-daily.xml");
        CurrencyRateContainer rateContainer = ecbCurrencyRatesMapper.parseXml(xml);
        Assertions.assertEquals(32, rateContainer.getRates().size());
        Assertions.assertEquals("Reference rates", rateContainer.getSubject());
        Assertions.assertEquals("European Central Bank", rateContainer.getSource());
    }

}
