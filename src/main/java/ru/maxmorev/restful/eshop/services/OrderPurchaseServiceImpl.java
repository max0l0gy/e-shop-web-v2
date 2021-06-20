package ru.maxmorev.restful.eshop.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.domain.OrderGrid;
import ru.maxmorev.restful.eshop.domain.ShoppingCart;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerOrderApi;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.request.CreateOrderRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderIdRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.request.PaymentInitialRequest;
import ru.maxmorev.restful.eshop.rest.request.PurchaseInfoRequest;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;
import ru.maxmorev.restful.eshop.rest.response.PurchaseDto;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus.AWAITING_PAYMENT;
import static ru.maxmorev.restful.eshop.util.ServiceExseptionSuppressor.suppress;

@Slf4j
@Service("orderPurchaseService")
@AllArgsConstructor
public class OrderPurchaseServiceImpl implements OrderPurchaseService {

    private final CustomerService customerService;
    private final NotificationService notificationService;
    private final EshopCustomerOrderApi eshopCustomerOrderApi;
    private final EshopCommodityApi eshopCommodityApi;
    private final PaymentServiceStrategy paymentServiceStrategy;

    @Override
    public CustomerOrder createOrderFor(CustomerDto customer) {
        //TODO REFACTORING https://github.com/users/max0l0gy/projects/1#card-62987896
        List<CustomerOrder> awaitingForPayment = eshopCustomerOrderApi
                .customerOrderListByStatus(customer.getId(), CustomerOrderStatus.AWAITING_PAYMENT.name());
        log.info("awaitingForPayment.size() = {}", awaitingForPayment.size());
        if (awaitingForPayment.size() > 0)
            //TODO move all code to service and refresh dateOfCreated to order
            return awaitingForPayment.get(0);
        ShoppingCart shoppingCart = eshopCustomerOrderApi.getShoppingCart(customer.getShoppingCartId());
        List<PurchaseInfoRequest> purchases = new ArrayList<>();
        shoppingCart.getShoppingSet().forEach(scs -> {
            CommodityBranchDto commodityBranch = eshopCommodityApi.getCommodityBranch(scs.getId().getBranchId());
            if (commodityBranch.getAmount().intValue() - scs.getPurchaseInfo().getAmount().intValue() < 0)
                throw new IllegalArgumentException("Amount of commodities is not available for purchase");
            CommodityDto commodity = eshopCommodityApi.findCommodityById(commodityBranch.getCommodityId());
            PurchaseInfoRequest purchaseInfo = new PurchaseInfoRequest();
            purchaseInfo.setAmount(scs.getPurchaseInfo().getAmount());
            purchaseInfo.setBranchId(commodityBranch.getId());
            purchaseInfo.setCommodityImageUri(commodity.getImages().get(0));
            purchaseInfo.setCommodityName(commodity.getName());
            purchaseInfo.setPrice(commodityBranch.getPrice());
            purchases.add(purchaseInfo);
        });
        //subtract branch amount in commodityApi
        purchases.forEach(purchaseInfo -> eshopCommodityApi.addAmountToBranch(purchaseInfo.getBranchId(), -purchaseInfo.getAmount().intValue()));
        //create order in OrderApi
        return eshopCustomerOrderApi.createOrder(new CreateOrderRequest(customer.getId(), purchases));
    }

    @Override
    public Optional<CustomerOrderDto> confirmPaymentOrder(Long orderId, Long customerId) {
        return findOrder(orderId, customerId)
                .filter(customerOrderDto -> AWAITING_PAYMENT.equals(customerOrderDto.getStatus()))
                .filter(this::checkOrderPaymentCompleted)
                .map(customerOrderDto -> confirmOrder(
                        OrderPaymentConfirmation.builder().
                                customerId(customerOrderDto.getCustomerId())
                                .orderId(customerOrderDto.getId())
                                .paymentId(customerOrderDto.getPaymentID())
                                .paymentProvider(customerOrderDto.getPaymentProvider().name())
                                .build()
                ))
                .map(this::convertForCustomer)
                ;
    }

    CustomerOrder confirmOrder(OrderPaymentConfirmation orderPaymentConfirmation) {
        log.info("Confirming order: {}", orderPaymentConfirmation.getOrderId());
        var order = eshopCustomerOrderApi.confirmOrder(orderPaymentConfirmation);
        var customer = customerService.findById(order.getCustomerId());
        var shoppingCart = eshopCustomerOrderApi.getShoppingCart(customer.getShoppingCartId());
        eshopCustomerOrderApi.cleanShoppingCart(shoppingCart.getId());
        notificationService.orderPaymentConfirmation(
                customer.getEmail(),
                customer.getFullName(),
                order.getId()
        );
        return order;
    }

    @Override
    public void cleanExpiredOrders() {
        List<CustomerOrder> expiredOrders = eshopCustomerOrderApi.findExpiredOrders();
        expiredOrders.forEach(o -> {

            Optional<CustomerOrderDto> confirmed = confirmPaymentOrder(o.getId(), o.getCustomerId());
            log.info("Order was in WAITING FOR PAYMENT status {} payment confirmed {}", o.getId(), confirmed.isPresent());
            if (confirmed.isEmpty()) {
                //return amount to commodity
                moveItemsFromOrderToBranch(o);
                //remove order
                eshopCustomerOrderApi.removeExpiredOrder(o.getId());
            }
        });
    }

    @Override
    public CustomerOrder setOrderStatus(Long id, CustomerOrderStatus status) {
        return eshopCustomerOrderApi.setOrderStatus(id, status.name());
    }


    @Override
    public Optional<CustomerOrderDto> findOrder(Long orderId, Long customerId) {
        return suppress(() -> eshopCustomerOrderApi.findCustomerOrder(customerId, orderId))
                .map(this::convertForCustomer)
                ;
    }

    private void moveItemsFromOrderToBranch(CustomerOrder o) {
        o.getPurchases().forEach(p -> eshopCommodityApi
                .addAmountToBranch(p.getId().getBranchId(),
                        p.getPurchaseInfo().getAmount()));
    }

    @Override
    public void cancelOrderByCustomer(Long orderId, Long customerId) {
        //TODO avoid switch, replace with strategy https://github.com/users/max0l0gy/projects/1#card-62987855
        CustomerOrder order = eshopCustomerOrderApi.findCustomerOrder(customerId, orderId);
        if (
                CustomerOrderStatus.AWAITING_PAYMENT.equals(order.getStatus())
                        || CustomerOrderStatus.PAYMENT_APPROVED.equals(order.getStatus())
        ) {
            moveItemsFromOrderToBranch(order);
            switch (order.getStatus()) {
                case PAYMENT_APPROVED:
                    CustomerOrderDto orderDto = convertForCustomer(order);
                    log.info("Starting order cancellation: {}", orderDto.getPaymentID());
                    paymentServiceStrategy
                            .getByPaymentProviderName(orderDto.getPaymentProvider().name())
                            .flatMap(paymentService -> paymentService.refundCapturedOrder(String.valueOf(orderDto.getId()), orderDto.getPaymentID()))
                            .filter(capturedOrderRefundResponse -> capturedOrderRefundResponse.getStatus().isCompleted())
                            .map(capturedOrderRefundResponse -> {
                                CustomerDto customer = customerService.findById(customerId);
                                notificationService.orderCancelCustomer(customer.getEmail(), customer.getFullName(), orderDto);
                                eshopCustomerOrderApi.customerOrderCancel(new OrderIdRequest(orderId, customerId));
                                //TODO create event in the system
                                return capturedOrderRefundResponse;
                            });
                    break;
                case AWAITING_PAYMENT:
                    eshopCustomerOrderApi.removeExpiredOrder(order.getId());
                    break;
            }
            return;
        }
        throw new RuntimeException("Implement logic with other OrderStatus");
    }

    CustomerOrderDto convert(CustomerOrder order) {
        List<PurchaseDto> purchases = new ArrayList<>();
        order.getPurchases().forEach(op -> {
            CommodityBranchDto cb = eshopCommodityApi.getCommodityBranch(op.getId().getBranchId());
            CommodityDto c = eshopCommodityApi.findCommodityById(cb.getCommodityId());
            purchases.add(PurchaseDto
                    .builder()
                    .name(c.getName())
                    .overview(c.getOverview())
                    .shortDescription(c.getShortDescription())
                    .type(c.getType().getName())
                    .commodityId(c.getId())
                    .dateOfCreation(c.getDateOfCreation())
                    .price(op.getPurchaseInfo().getPrice())
                    .amount(op.getPurchaseInfo().getAmount())
                    .branchId(cb.getId())
                    .currency(Currency.getInstance(cb.getCurrency()))
                    .images(c.getImages())
                    .attributes(cb.getAttributes())
                    .build()
            );

        });

        return CustomerOrderDto.builder()
                .customerId(order.getCustomerId())
                .dateOfCreation(order.getDateOfCreation())
                .id(order.getId())
                .paymentID(order.getPaymentID())
                .paymentProvider(order.getPaymentProvider())
                .status(order.getStatus())
                .purchases(purchases)
                .totalPrice(purchases.stream().mapToDouble(p -> p.getAmount() * p.getPrice()).sum())
                .build();
    }

    public CustomerOrderDto convertForCustomer(CustomerOrder order) {
        CustomerOrderDto orderDto = convert(order);
        orderDto.setActions(CustomerOrderDto.getCustomerAvailableActions(orderDto.getStatus()));
        return orderDto;
    }

    CustomerOrderDto convertForAdmin(CustomerOrder order) {
        CustomerOrderDto orderDto = convert(order);
        orderDto.setActions(CustomerOrderDto.getAvailableActions(orderDto.getStatus()));
        return orderDto;
    }


    @Override
    public List<CustomerOrderDto> findOrderListForCustomer(Long customerId) {
        return eshopCustomerOrderApi
                .customerOrderList(customerId)
                .stream()
                .map(this::convertForCustomer)
                .collect(Collectors.toList());
    }

    OrderGridDto convert(OrderGrid orderGrid) {
        return OrderGridDto.builder()
                .currentPage(orderGrid.getCurrentPage())
                .totalPages(orderGrid.getTotalPages())
                .totalRecords(orderGrid.getTotalRecords())
                .orderData(orderGrid
                        .getOrderData()
                        .stream()
                        .map(this::convertForAdmin)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public OrderGridDto getOrdersForAdmin(Integer page, Integer rows, String sortBy, String order) {
        return convert(eshopCustomerOrderApi.adminAllCustomerOrderList(page, rows, sortBy, order));
    }

    @Override
    public Optional<CustomerOrder> paymentInitial(PaymentInitialRequest paymentInitialRequest) {
        return Optional.ofNullable(eshopCustomerOrderApi.paymentInitial(paymentInitialRequest).getData());
    }

    private boolean checkOrderPaymentCompleted(CustomerOrderDto customerOrderDto) {
        return paymentServiceStrategy
                .getByPaymentProviderName(customerOrderDto.getPaymentProvider().name())
                .flatMap(paymentService -> paymentService.getOrder(customerOrderDto.getPaymentID()))
                .map(capturedOrder -> capturedOrder.getStatus().isCompleted())
                .orElse(false);
    }
}
