package ru.maxmorev.restful.eshop.feignclient;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Order;
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
public class PayPalApiTest {
    @Autowired
    private PayPalApi payPalApi;

    @Test
    public void loadContext() {
        //is context ok
    }

    @Test
    @SneakyThrows
    public void checkOrderCompleted() {
        Order order = payPalApi.getOrder("2CV55440UT6646827");
        Assertions.assertEquals("2CV55440UT6646827", order.getId());
        Assertions.assertEquals(1, order.getPurchaseUnits().size());
        Assertions.assertEquals("1.00", order.getPurchaseUnits().get(0).getAmount().getValue());
        Assertions.assertEquals("USD", order.getPurchaseUnits().get(0).getAmount().getCurrencyCode());
        Assertions.assertEquals(1, order.getPurchaseUnits().get(0).getPayments().getCaptures().size());
        Assertions.assertEquals("COMPLETED", order.getPurchaseUnits().get(0).getPayments().getCaptures().get(0).getStatus().name());
        Assertions.assertEquals("85W962518S850170V", order.getPurchaseUnits().get(0).getPayments().getCaptures().get(0).getId());
    }


}
