package ru.maxmorev.restful.eshop.feignclient.domain;

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
