package ru.maxmorev.restful.eshop.feignclient.domain.ses.templates;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;

import java.util.Map;

public class VerifyEmailTemplate {

    public MailSendRequest create(String destination, String name, String code) {
        return new MailSendRequest(
                "VerifyEmail",
                destination,
                Map.of("name", name, "code", code, "site", "titsonfire.store"));
    }

}
