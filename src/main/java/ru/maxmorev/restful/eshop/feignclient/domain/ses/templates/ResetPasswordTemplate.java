package ru.maxmorev.restful.eshop.feignclient.domain.ses.templates;

import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.ResetPassword;

import java.util.Map;

public class ResetPasswordTemplate {
    public MailSendRequest create(ResetPassword resetPassword) {
        return new MailSendRequest(
                "ResetPassword",
                resetPassword.getEmail(),
                Map.of("name", resetPassword.getName(),
                        "site-url", resetPassword.getSiteUrl(),
                        "reset-password-url", resetPassword.getResetPasswordUrl()
                ));
    }
}
