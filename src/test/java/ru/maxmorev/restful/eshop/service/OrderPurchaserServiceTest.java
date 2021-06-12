package ru.maxmorev.restful.eshop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.PurchaseDto;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
public class OrderPurchaserServiceTest {
    @Autowired
    OrderPurchaseService orderPurchaseService;

    @Test
    public void loadContext() {
        assertNotNull(orderPurchaseService);
    }

    @Test
    public void findOrderListForCustomer() {
        List<CustomerOrderDto> orders = orderPurchaseService.findOrderListForCustomer(101L);
        assertEquals(1, orders.size());
        CustomerOrderDto order = orders.get(0);
        assertEquals(90.0, order.getTotalPrice());
        assertEquals(2, order.getPurchases().size());
        assertEquals(2, order.getPurchases().stream().mapToInt(PurchaseDto::getAmount).sum());
        assertEquals("PAYMENT_APPROVED", order.getStatus().name());
        assertEquals("Yoomoney", order.getPaymentProvider().name());
        assertEquals("PAYPALZX1293", order.getPaymentID());
        assertEquals(101, order.getCustomerId());
        assertEquals(25, order.getId());

    }

}
