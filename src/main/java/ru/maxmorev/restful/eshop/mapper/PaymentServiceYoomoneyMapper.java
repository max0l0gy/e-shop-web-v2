package ru.maxmorev.restful.eshop.mapper;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.domain.CapturedOrder;
import ru.maxmorev.restful.eshop.domain.CapturedOrderAmount;
import ru.maxmorev.restful.eshop.domain.CapturedOrderStatus;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.EmbeddedPaymentResponse;

import java.util.Map;
import java.util.Optional;

@Component
public class PaymentServiceYoomoneyMapper {

    private final Map<String, String> STATUSES = ImmutableMap.of(
            "pending", "PENDING",
            "waiting_for_capture", "UNDEFINED",
            "succeeded","COMPLETED"
    );

    public CapturedOrder of(EmbeddedPaymentResponse paymentResponse) {
        if (paymentResponse == null) {
            return null;
        }
        return new CapturedOrder()
                .setAmount(
                        new CapturedOrderAmount()
                                .setCurrencyCode(paymentResponse.getAmount().getCurrency())
                                .setValue(paymentResponse.getAmount().getValue().toPlainString())
                )
                .setCaptureId(paymentResponse.getId())
                .setDescription(paymentResponse.getDescription())
                .setStatus(new CapturedOrderStatus().setStatus(mapStatus(paymentResponse.getStatus())))
                ;
    }

    String mapStatus(String fromPayment) {
        return Optional.ofNullable(STATUSES.get(fromPayment))
                .orElse("UNDEFINED");
    }
}
