package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.config.ManagerConfig;
import ru.maxmorev.restful.eshop.feignclient.MailServiceApi;
import ru.maxmorev.restful.eshop.feignclient.domain.MailSendResponse;
import ru.maxmorev.restful.eshop.feignclient.domain.OrderPaymentCanceledCustomerAdminTemplate;
import ru.maxmorev.restful.eshop.feignclient.domain.OrderPaymentCanceledCustomerTemplate;
import ru.maxmorev.restful.eshop.feignclient.domain.OrderPaymentConfirmedAdminTemplate;
import ru.maxmorev.restful.eshop.feignclient.domain.OrderPaymentConfirmedTemplate;
import ru.maxmorev.restful.eshop.feignclient.domain.VerifyEmailTemplate;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MailServiceApi mailServiceApi;
    private final ManagerConfig managerConfig;

    @Override
    public MailSendResponse emailVerification(String email, String name, String code) {
        return mailServiceApi.sendTemplate(
                new VerifyEmailTemplate()
                        .create(email,
                                name,
                                code
                        ));
    }

    @Override
    public MailSendResponse orderPaymentConfirmation(String email, String name, long orderId) {
        mailServiceApi.sendTemplate(
                new OrderPaymentConfirmedAdminTemplate()
                        .create(
                                managerConfig.getEmail(),
                                orderId
                        ));
        return mailServiceApi.sendTemplate(
                new OrderPaymentConfirmedTemplate()
                        .create(email,
                                name,
                                orderId));
    }

    @Override
    public MailSendResponse orderCancelCustomer(String email, String name, CustomerOrderDto order) {
        mailServiceApi.sendTemplate(
                new OrderPaymentCanceledCustomerAdminTemplate()
                        .create(
                                managerConfig.getEmail(),
                                order
                        ));
        return mailServiceApi.sendTemplate(
                new OrderPaymentCanceledCustomerTemplate()
                        .create(email,
                                name,
                                order));
    }
}
