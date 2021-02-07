package ru.maxmorev.restful.eshop.rest.response;

import lombok.Builder;
import lombok.Getter;
import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.annotation.PaymentProvider;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class CustomerOrderDto extends JsonMappedValue {
    private Long id;
    private Long customerId;
    private Date dateOfCreation;
    private CustomerOrderStatus status;
    private PaymentProvider paymentProvider;
    private String paymentID;
    private List<PurchaseDto> purchases;
    private List<Action> actions;
    private double totalPrice;

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public static List<Action> getAvailableActions(CustomerOrderStatus status) {
        List<Action> actions = new ArrayList<>();
        switch (status) {
            case PAYMENT_APPROVED:
                actions.add(Action.builder()
                        .action(CustomerOrderStatus.PREPARING_TO_SHIP.name())
                        .id(2)
                        .build());
                break;
            case PREPARING_TO_SHIP:
                actions.add(Action.builder()
                        .action(CustomerOrderStatus.DISPATCHED.name())
                        .id(3)
                        .build());
                break;
        }
        return actions;
    }

    public static List<Action> getCustomerAvailableActions(CustomerOrderStatus status) {
        List<Action> actions = new ArrayList<>();
        switch (status) {
            case PAYMENT_APPROVED:
                actions.add(Action.builder()
                        .action(CustomerOrderStatus.CANCELED_BY_CUSTOMER.name())
                        .id(2)
                        .build());
                break;

        }
        return actions;
    }

}
