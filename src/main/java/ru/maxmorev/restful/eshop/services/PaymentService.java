package ru.maxmorev.restful.eshop.services;

import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundResponse;

import java.util.Optional;

public interface PaymentService {

    Optional<CapturedOrder> getOrder(String orderId);
    Optional<CapturedOrderRefundResponse> refundCapturedOrder(String captureId);

}
