package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RefundRequest {
    private Amount amount;
    @JsonProperty("payment_id")
    private String paymentId;
}
