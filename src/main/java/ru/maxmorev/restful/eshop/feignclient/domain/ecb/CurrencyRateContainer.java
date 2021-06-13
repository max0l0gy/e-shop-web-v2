package ru.maxmorev.restful.eshop.feignclient.domain.ecb;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CurrencyRateContainer {
    private String subject;
    private String source;
    private List<CurrencyRate> rates;
    private String time;

    @Data
    @Accessors(chain = true)
    public static class CurrencyRate {
        private String currency;
        private BigDecimal rate;
    }

    public List<CurrencyRate> addRate(CurrencyRate rate) {
        if (null == rates)
            rates = new ArrayList<>();
        rates.add(rate);
        return rates;
    }

    public Optional<CurrencyRate> findByCurrency(String currencyCode) {
        return rates.stream()
                .filter(currencyRate -> currencyCode.equals(currencyRate.getCurrency()))
                .findFirst();
    }

    public Optional<BigDecimal> convertUsdToRub(BigDecimal usdValue) {
        return findByCurrency("USD")
                .flatMap(usdRate -> findByCurrency("RUB")
                        .map(rubRate -> BigDecimal.ONE.divide(usdRate.getRate(), RoundingMode.HALF_UP).multiply(rubRate.getRate()).multiply(usdValue).setScale(2, RoundingMode.CEILING))
                )
                ;
    }

}
