package ru.maxmorev.restful.eshop.feignclient.domain.ses.templates;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;

import java.util.Map;

public class PreparingToShipTemplate {

    public MailSendRequest create(String destination, String name, long orderId) {
        return new MailSendRequest(
                "PreparingToShip",
                destination,
                Map.of("name", name, "order-id", String.valueOf(orderId)));
    }

}
