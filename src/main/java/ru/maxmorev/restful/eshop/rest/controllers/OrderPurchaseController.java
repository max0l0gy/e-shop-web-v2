package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.request.OrderIdRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.Message;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderPurchaseController {

    private final MessageSource messageSource;
    private final OrderPurchaseService orderPurchaseService;

    @PostMapping(path = Constants.REST_CUSTOMER_URI + "order/confirm/")
    @ResponseBody
    Message confirmOrder(@RequestBody
                         @Valid OrderPaymentConfirmation orderPaymentConfirmation,
                         Locale locale) {
        return orderPurchaseService.confirmPaymentOrder(orderPaymentConfirmation.getOrderId(), orderPaymentConfirmation.getCustomerId())
                .map(order -> new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale)))
                .orElseGet(() -> new Message(
                                Message.ERROR,
                                messageSource
                                        .getMessage(
                                                "order.payment.confirmation.error",
                                                new Object[]{
                                                        orderPaymentConfirmation.getOrderId(),
                                                        orderPaymentConfirmation.getPaymentId()
                                                },
                                                locale)
                        )
                );
    }

    @GetMapping(path = Constants.REST_CUSTOMER_URI + "order/{id}/customer/{customerId}")
    @ResponseBody
    CustomerOrderDto customerOrder(
            @PathVariable(name = "customerId") Long customerId,
            @PathVariable(name = "id") Long orderId,
            Locale locale) {
        return orderPurchaseService.findOrder(orderId, customerId)
                .orElseThrow(()->new RuntimeException("Order not found"));
    }

    @GetMapping(path = Constants.REST_CUSTOMER_URI + "order/list/{customerId}")
    @ResponseBody
    List<CustomerOrderDto> customerOrderList(@PathVariable(name = "customerId", required = true) Long customerId, Locale locale) {
        //TODO check auth customer.id with id in PathVariable
        return orderPurchaseService.findOrderListForCustomer(customerId);
    }

    @PutMapping(path = Constants.REST_CUSTOMER_URI + "order/cancellation/")
    @ResponseBody
    Message customerOrderCancel(
            @Valid @RequestBody OrderIdRequest order,
            Locale locale) {
        log.info("Customer Order Cancellation order.id={} started", order.getOrderId());
        orderPurchaseService.cancelOrderByCustomer(order.getOrderId(), order.getCustomerId());
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }


    @PutMapping(path = Constants.REST_MANAGER_URI + "order/{id}/{status}")
    @ResponseBody
    public Message setOrderStatus(
            @PathVariable(name = "status", required = true) String status,
            @PathVariable(name = "id", required = true) Long id,
            Locale locale) {
        orderPurchaseService.setOrderStatus(id, CustomerOrderStatus.valueOf(status));
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }

    @GetMapping(path = Constants.REST_MANAGER_URI + "order/list/")
    @ResponseBody
    OrderGridDto adminAllCustomerOrderList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            Locale locale) {
        return orderPurchaseService
                .getOrdersForAdmin(page,
                        rows,
                        sortBy,
                        order);
    }

}
