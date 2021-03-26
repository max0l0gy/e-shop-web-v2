package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CapturedOrder {
    private CapturedOrderStatus status;
    private CapturedOrderAmount amount;
    private String description;
    private String orderId;
    private String captureId;
}
