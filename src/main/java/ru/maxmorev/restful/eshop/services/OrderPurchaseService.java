package ru.maxmorev.restful.eshop.services;

import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.domain.CapturedOrderStatus;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;

import java.util.List;

public interface OrderPurchaseService {

    CustomerOrderDto findOrder(Long id, Long customerId);

    CustomerOrder createOrderFor(Customer customer);

    CapturedOrderStatus checkOrder(OrderPaymentConfirmation orderPaymentConfirmation);

    CustomerOrder confirmPaymentOrder(OrderPaymentConfirmation orderPaymentConfirmation);

    void cancelOrderByCustomer(Long orderId, Long customerId);

    void cleanExpiredOrders();

    CustomerOrder setOrderStatus(Long id, CustomerOrderStatus status);

    List<CustomerOrderDto> findOrderListForCustomer(Long customerId);

    OrderGridDto getOrdersForAdmin(Integer page, Integer rows, String sortBy, String order);

}
