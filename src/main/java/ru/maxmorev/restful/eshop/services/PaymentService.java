package ru.maxmorev.restful.eshop.services;

import ru.maxmorev.restful.eshop.domain.CapturedOrder;

import java.util.Optional;

public interface PaymentService {

    Optional<CapturedOrder> getOrder(String orderId);
    Optional<CapturedOrder> refundCapturedOrder(String captureId);

}
