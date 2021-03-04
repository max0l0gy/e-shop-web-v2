package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.feignclient.PayPalApi;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Order;
import ru.maxmorev.restful.eshop.mapper.PaymentServicePayPalMapper;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServicePayPal implements PaymentService {

    private final PayPalApi payPalApi;
    private final PaymentServicePayPalMapper mapper;

    @Override
    public Optional<CapturedOrder> getOrder(String orderId) {
        Order order = null;
        try {
            order = payPalApi.getOrder(orderId);
        } catch (Exception exception) {
            log.error("PayPal get order error", exception);
        }
        return Optional.ofNullable(mapper.of(order));
    }

    @Override
    public Optional<CapturedOrder> refundCapturedOrder(String captureId) {
        return Optional.empty();
    }
}
