package ru.maxmorev.restful.eshop.rest.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentInitialRequest {
    private Long orderId;
    private String paymentID;
    private String paymentProvider;
}
