package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRateService {

    String getBaseCurrency() {
        return "EUR";
    }

}
