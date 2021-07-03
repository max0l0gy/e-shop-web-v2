package ru.maxmorev.restful.eshop.feignclient.domain.ses.templates;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;

import java.util.Map;

public class OrderPaymentCanceledCustomerAdminTemplate {
    public MailSendRequest create(String destination, CustomerOrderDto order) {
        return new MailSendRequest(
                "OrderPaymentCanceledAdmin",
                destination,
                Map.of("order-id", order.getId().toString(),
                        "order-price", String.valueOf(order.getTotalPrice()),
                        "order-price-currency", order.getPurchases().get(0).getCurrency().getCurrencyCode(),
                        "site", "titsonfire.store"));
    }
}
