package ru.maxmorev.restful.eshop.services;

import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.domain.CapturedOrderStatus;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.request.PaymentInitialRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;

import java.util.List;
import java.util.Optional;

public interface OrderPurchaseService {

    CustomerOrderDto findOrder(Long orderId, Long customerId);

    CustomerOrder createOrderFor(CustomerDto customer);

    CapturedOrderStatus checkOrder(OrderPaymentConfirmation orderPaymentConfirmation);

    Optional<CustomerOrder> confirmPaymentOrder(OrderPaymentConfirmation orderPaymentConfirmation);

    void cancelOrderByCustomer(Long orderId, Long customerId);

    void cleanExpiredOrders();

    CustomerOrder setOrderStatus(Long id, CustomerOrderStatus status);

    List<CustomerOrderDto> findOrderListForCustomer(Long customerId);

    OrderGridDto getOrdersForAdmin(Integer page, Integer rows, String sortBy, String order);

    Optional<CustomerOrder> paymentInitial(PaymentInitialRequest paymentInitialRequest);

    CustomerOrderDto convertForCustomer(CustomerOrder order);

}
