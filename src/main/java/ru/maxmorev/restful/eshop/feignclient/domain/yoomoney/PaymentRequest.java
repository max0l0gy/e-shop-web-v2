package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PaymentRequest {
    private String idempotenceKey;
    private BigDecimal amount;
    private String description;
}
