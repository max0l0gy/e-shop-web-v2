package ru.maxmorev.restful.eshop.feignclient.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResetPassword {
    private String email;
    private String name;
    private String siteUrl;
    private String resetPasswordUrl;
}
