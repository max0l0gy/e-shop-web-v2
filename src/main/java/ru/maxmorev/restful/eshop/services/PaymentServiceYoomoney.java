package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundResponse;
import ru.maxmorev.restful.eshop.feignclient.YoomoneyApi;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.EmbeddedPaymentResponse;
import ru.maxmorev.restful.eshop.mapper.PaymentServiceYoomoneyMapper;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceYoomoney implements PaymentService {
    private final YoomoneyApi yoomoneyApi;
    private final PaymentServiceYoomoneyMapper mapper;

    @Override
    public Optional<CapturedOrder> getOrder(String orderId) {
        EmbeddedPaymentResponse order = null;
        try {
            order = yoomoneyApi.getPayment(orderId).getData();
        } catch (Exception exception) {
            log.error("PayPal get order error", exception);
        }
        return Optional.ofNullable(mapper.of(order));
    }

    @Override
    public Optional<CapturedOrderRefundResponse> refundCapturedOrder(String captureId) {
        return Optional.empty();
    }
}
