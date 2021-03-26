package ru.maxmorev.restful.eshop.mapper;

import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.domain.*;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Capture;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Order;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.Purchase;
import ru.maxmorev.restful.eshop.feignclient.domain.paypal.RefundResponse;

@Component
public class PaymentServicePayPalMapper {

    public CapturedOrderRefundResponse of(RefundResponse refundResponse) {
        if(refundResponse == null) {
            return null;
        }
        return  new CapturedOrderRefundResponse()
                .setId(refundResponse.getId())
                .setStatus(
                        new CapturedOrderRefundStatus()
                        .setStatus(refundResponse.getStatus().name())
                );
    }

    public CapturedOrder of(Order payPalOrder) {
        if (payPalOrder == null) {
            return null;
        }
        Capture capture = getCapture(payPalOrder);
        return new CapturedOrder()
                .setAmount(
                        new CapturedOrderAmount()
                                .setCurrencyCode(capture.getAmount().getCurrencyCode())
                                .setValue(capture.getAmount().getValue())
                )
                .setCaptureId(capture.getId())
                .setDescription(getPurchase(payPalOrder).getDescription())
                .setStatus(new CapturedOrderStatus().setStatus(capture.getStatus().name()))
                .setOrderId(payPalOrder.getId())
                ;
    }

    private Capture getCapture(Order payPalOrder) {
        return payPalOrder.getPurchaseUnits().get(0).getPayments().getCaptures().get(0);
    }

    private Purchase getPurchase(Order payPalOrder) {
        return payPalOrder.getPurchaseUnits().get(0);
    }
}
