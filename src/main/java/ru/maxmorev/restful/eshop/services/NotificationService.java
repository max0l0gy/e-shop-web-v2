package ru.maxmorev.restful.eshop.services;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendResponse;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;

public interface NotificationService {

    MailSendResponse emailVerification(String email, String name, String code);
    MailSendResponse orderPaymentConfirmation(String email, String name, long orderId);
    MailSendResponse orderCancelCustomer(String email, String name, CustomerOrderDto order);

}
