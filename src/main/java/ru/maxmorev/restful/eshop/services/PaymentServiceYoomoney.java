package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundResponse;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundStatus;
import ru.maxmorev.restful.eshop.feignclient.YoomoneyApi;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.EmbeddedPaymentResponse;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RefundRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RestResponse;
import ru.maxmorev.restful.eshop.mapper.PaymentServiceYoomoneyMapper;

import java.util.Objects;
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
    public Optional<CapturedOrderRefundResponse> refundCapturedOrder(String orderId, String captureId) {
        log.info("Starting payment refund: {}", captureId);
        return Optional.ofNullable(yoomoneyApi.getPayment(captureId))
                .map(paymentResponse -> {
                    log.info("response status: {}", paymentResponse.getStatus());
                    log.info("response data not empty {}", Objects.nonNull(paymentResponse.getData()));
                    return paymentResponse;
                })
                .filter(restResponse -> Objects.nonNull(restResponse.getData()))
                .map(RestResponse::getData)
                .filter(EmbeddedPaymentResponse::getPaid)
                .map(payment -> new RefundRequest()
                        .setAmount(payment.getAmount())
                        .setPaymentId(payment.getId())
                )
                .map(refundRequest -> yoomoneyApi.refunds(orderId, refundRequest))
                .map(restResponse -> {
                    log.info("response status: {}", restResponse.getStatus());
                    log.info("response data not empty {}", Objects.nonNull(restResponse.getData()));
                    return restResponse;
                })
                .filter(restResponse -> Objects.nonNull(restResponse.getData()))
                .map(RestResponse::getData)
                .map(refundResponse -> new CapturedOrderRefundResponse()
                        .setPaymentId(refundResponse.getPaymentId())
                        .setId(refundResponse.getId())
                        .setAmount(refundResponse.getAmount())
                        .setStatus(map(refundResponse.getStatus()))
                );
    }

    private CapturedOrderRefundStatus map(String status) {
        if ("succeeded".equals(status))
            return new CapturedOrderRefundStatus().setStatus(CapturedOrderRefundStatus.COMPLETED);
        if ("canceled".equals(status))
            return new CapturedOrderRefundStatus().setStatus(CapturedOrderRefundStatus.CANCELLED);
        return new CapturedOrderRefundStatus().setStatus(CapturedOrderRefundStatus.UNDEFINED);
    }
}
