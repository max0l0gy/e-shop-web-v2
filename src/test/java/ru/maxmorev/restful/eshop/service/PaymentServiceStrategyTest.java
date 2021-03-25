package ru.maxmorev.restful.eshop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import ru.maxmorev.restful.eshop.annotation.PaymentProvider;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundResponse;
import ru.maxmorev.restful.eshop.domain.CapturedOrderRefundStatus;
import ru.maxmorev.restful.eshop.services.PaymentServiceStrategy;

import java.util.Optional;

@Slf4j
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentServiceStrategyTest {
    @Autowired
    private PaymentServiceStrategy paymentServiceStrategy;

    @Test
    public void isPaypalStrategyExist() {
        Assertions.assertTrue(paymentServiceStrategy.getByPaymentProviderName(PaymentProvider.Paypal.name()).isPresent());
    }

    @Test
    public void getOrderCompleted() {
        paymentServiceStrategy
                .getByPaymentProviderName((PaymentProvider.Paypal.name()))
                .ifPresent(paymentService -> {
                    Optional<CapturedOrder> order = paymentService.getOrder("2CV55440UT6646827");
                    Assertions.assertTrue(order.isPresent());
                    order.ifPresent(capturedOrder -> {
                        Assertions.assertEquals("2CV55440UT6646827", capturedOrder.getOrderId());
                        Assertions.assertEquals("85W962518S850170V", capturedOrder.getCaptureId());
                        Assertions.assertEquals("titsonfire.store Payment for order #401", capturedOrder.getDescription());
                        Assertions.assertEquals("COMPLETED", capturedOrder.getStatus().getStatus());
                        Assertions.assertEquals("1.00", capturedOrder.getAmount().getValue());
                        Assertions.assertEquals("USD", capturedOrder.getAmount().getCurrencyCode());
                        Assertions.assertTrue(capturedOrder.getStatus().isCompleted());
                    });
                });
    }

     @Test
    public void refundCompleted() {
        String captureId = "85W962518S850170V";
         paymentServiceStrategy
                 .getByPaymentProviderName((PaymentProvider.Paypal.name()))
                 .ifPresent(paymentService -> {
                     Optional<CapturedOrderRefundResponse> order = paymentService.refundCapturedOrder(captureId);
                     Assertions.assertTrue(order.isPresent());
                     order.ifPresent(refundResponse -> {
                         Assertions.assertEquals("4BH36132M15490109", refundResponse.getId());
                         Assertions.assertEquals(CapturedOrderRefundStatus.COMPLETED, refundResponse.getStatus().getStatus());
                         Assertions.assertTrue(refundResponse.getStatus().isCompleted());
                     });
                 });
    }

}
