package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Amount {
    private  BigDecimal value;
    private String currency = "RUB";
}
