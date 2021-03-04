package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
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

        orderPurchaseService.confirmPaymentOrder(orderPaymentConfirmation);
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }

    @GetMapping(path = Constants.REST_CUSTOMER_URI + "order/{id}/customer/{customerId}")
    @ResponseBody
    CustomerOrderDto customerOrder(
            @PathVariable(name = "customerId") Long customerId,
            @PathVariable(name = "id") Long orderId,
            Locale locale) {

        return orderPurchaseService.findOrder(orderId, customerId);
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
