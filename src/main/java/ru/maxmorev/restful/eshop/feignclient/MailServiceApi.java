package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.maxmorev.restful.eshop.feignclient.domain.MailSendRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.MailSendResponse;

@FeignClient(name = "mailService", url = "${external.ses.url}")
public interface MailServiceApi {
    @PostMapping(value = "/send/template/", consumes = "application/json")
    MailSendResponse sendTemplate(@RequestBody MailSendRequest mailSendRequest);
}