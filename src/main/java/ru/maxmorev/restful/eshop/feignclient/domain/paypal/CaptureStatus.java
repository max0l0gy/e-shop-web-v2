package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

public enum CaptureStatus {
    COMPLETED,
    DECLINED,
    PARTIALLY_REFUNDED,
    PENDING,
    REFUNDED;
}
