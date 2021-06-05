package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class EmbeddedPaymentRequest {
    Amount amount;
    Confirmation confirmation;
    Boolean capture;
    String description;

}
