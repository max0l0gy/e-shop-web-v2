package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ecb-api", url = "${external.ecb.url}")
public interface EuropeanCentralBankCurrencyRateApi {
    @GetMapping("${external.ecb.daily-xml}")
    String getCurrenciesRateXml();
}
