package ru.maxmorev.restful.eshop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.services.PaymentServicePayPal;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@RunWith(SpringRunner.class)
public class PaymentServicePayPalTest {
    @Autowired
    PaymentServicePayPal paymentServicePayPal;

    @Test
    public void getOrderCompleted() {
        Optional<CapturedOrder> order = paymentServicePayPal.getOrder("2CV55440UT6646827");
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
    }
}
