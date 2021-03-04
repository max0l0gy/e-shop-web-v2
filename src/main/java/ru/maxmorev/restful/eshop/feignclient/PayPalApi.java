package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Order;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.RefundResponse;

@FeignClient(name = "eshop-paypal-api", url = "${external.paypal.url}")
public interface PayPalApi {

    @GetMapping(path = "/v1/order/{id}")
    Order getOrder(@PathVariable(name = "id") String orderId);

    @PostMapping("/capture/refund/{captureId}")
    RefundResponse refund(@PathVariable(name = "captureId") String captureId);

}
