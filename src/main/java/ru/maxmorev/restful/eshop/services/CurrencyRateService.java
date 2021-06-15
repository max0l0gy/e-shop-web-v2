package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.feignclient.EuropeanCentralBankCurrencyRateApi;
import ru.maxmorev.restful.eshop.feignclient.domain.ecb.CurrencyRateContainer;
import ru.maxmorev.restful.eshop.mapper.EcbCurrencyRatesMapper;

@Slf4j
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"currencyRate"})
public class CurrencyRateService {

    private final EuropeanCentralBankCurrencyRateApi centralBankCurrencyRateApi;
    private final EcbCurrencyRatesMapper mapper;

    public String getBaseCurrency() {
        return "EUR";
    }

    @Cacheable
    public CurrencyRateContainer getCurrencyRateContainer() {
        log.info("Getting currency rate");
        return mapper.parseXml(centralBankCurrencyRateApi.getCurrenciesRateXml());
    }

}
