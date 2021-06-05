package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.EmbeddedPaymentResponse;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.PaymentRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RefundRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RefundResponse;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RestResponse;

@FeignClient(name = "eshop-yoomoney-api", url = "${external.yoomoney.url}")
public interface YoomoneyApi {
    @PostMapping("/v1/payments")
    RestResponse<EmbeddedPaymentResponse> initial(PaymentRequest paymentRequest);

    @GetMapping("/v1/payments/{paymentId}")
    RestResponse<EmbeddedPaymentResponse> getPayment(@PathVariable("paymentId") String paymentId);

    @PostMapping("/v1/refunds")
    RestResponse<RefundResponse> refunds(
            @RequestHeader("Idempotence-Key") String idempotenceKey,
            RefundRequest refundRequest);
}
