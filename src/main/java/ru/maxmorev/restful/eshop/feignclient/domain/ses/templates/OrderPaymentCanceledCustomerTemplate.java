package ru.maxmorev.restful.eshop.feignclient.domain.ses.templates;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;

import java.util.Map;

public class OrderPaymentCanceledCustomerTemplate {
    public MailSendRequest create(String destination, String name, CustomerOrderDto order) {
        return new MailSendRequest(
                "OrderPaymentCanceledCustomer",
                destination,
                Map.of("name", name,
                        "order-id", order.getId().toString(),
                        "order-price", String.valueOf(order.getTotalPrice()),
                        "order-price-currency", order.getPurchases().get(0).getCurrency().getCurrencyCode(),
                        "site", "titsonfire.store"));
    }
}
