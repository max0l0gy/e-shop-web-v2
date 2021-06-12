package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.Amount;

@Getter
@Setter
@Accessors(chain = true)
public class CapturedOrderRefundResponse {
    private String id;
    private CapturedOrderRefundStatus status;
    private String paymentId;
    private Amount amount;
}
