package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentServiceStrategy {
    private final PaymentServicePayPal paymentServicePayPal;
    private final PaymentServiceYoomoney paymentServiceYoomoney;
    private Map<String, PaymentService> paymentServiceMapMapByProviderName;

    public Optional<PaymentService> getByPaymentProviderName(String paymentProvider) {
        return Optional.ofNullable(paymentServiceMapMapByProviderName.get(paymentProvider));
    }

    @PostConstruct
    public void initStrategy() {
        paymentServiceMapMapByProviderName = new HashMap<>();
        paymentServiceMapMapByProviderName.put("Paypal", paymentServicePayPal);
        paymentServiceMapMapByProviderName.put("Yoomoney", paymentServiceYoomoney);
    }

}
