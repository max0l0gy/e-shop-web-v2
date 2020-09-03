package ru.maxmorev.restful.eshop.feignclient.domain;

import java.util.Map;


public class OrderPaymentConfirmedAdminTemplate {

    public MailSendRequest create(String destination, long orderId) {
        return new MailSendRequest(
                "OrderPaymentConfirmedAdmin",
                destination,
                Map.of("order-id", String.valueOf(orderId)));
    }

}
