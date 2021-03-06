package ru.maxmorev.restful.eshop.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.domain.OrderGrid;
import ru.maxmorev.restful.eshop.domain.ShoppingCart;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerOrderApi;
import ru.maxmorev.restful.eshop.rest.request.CreateOrderRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderIdRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.request.PurchaseInfoRequest;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;
import ru.maxmorev.restful.eshop.rest.response.PurchaseDto;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("orderPurchaseService")
@AllArgsConstructor
public class OrderPurchaseServiceImpl implements OrderPurchaseService {

    private final CustomerService customerService;
    private final NotificationService notificationService;
    private final EshopCustomerOrderApi eshopCustomerOrderApi;
    private final EshopCommodityApi eshopCommodityApi;

    @Override
    public CustomerOrder createOrderFor(Customer customer) {
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
        purchases.forEach(purchaseInfo -> {
            eshopCommodityApi.addAmountToBranch(purchaseInfo.getBranchId(), -purchaseInfo.getAmount().intValue());
        });
        //create order in OrderApi
        return eshopCustomerOrderApi.createOrder(new CreateOrderRequest(customer.getId(), purchases));
    }


    @Override
    public CustomerOrder confirmPaymentOrder(OrderPaymentConfirmation orderPaymentConfirmation) {
        CustomerOrder order = eshopCustomerOrderApi.confirmOrder(orderPaymentConfirmation);
        Customer customer = customerService.findById(order.getCustomerId());
        ShoppingCart shoppingCart = eshopCustomerOrderApi.getShoppingCart(customer.getShoppingCartId());
        //clean shopping cart
        eshopCustomerOrderApi.cleanShoppingCart(shoppingCart.getId());
        //notify customer
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
            //return amount to commodity
            moveItemsFromOrderToBranch(o);
            //remove order
            eshopCustomerOrderApi.removeExpiredOrder(o.getId());
        });
    }

    @Override
    public CustomerOrder setOrderStatus(Long id, CustomerOrderStatus status) {
        return eshopCustomerOrderApi.setOrderStatus(id, status.name());
    }


    @Override
    public CustomerOrderDto findOrder(Long id, Long customerId) {
        return convertForCustomer(eshopCustomerOrderApi.findCustomerOrder(customerId, id));
    }

    private void moveItemsFromOrderToBranch(CustomerOrder o) {
        o.getPurchases().forEach(p -> eshopCommodityApi
                .addAmountToBranch(p.getId().getBranchId(),
                        p.getPurchaseInfo().getAmount()));
    }

    @Override
    public void cancelOrderByCustomer(Long orderId, Long customderId) {
        CustomerOrder order = eshopCustomerOrderApi.findCustomerOrder(customderId, orderId);
        if (
                CustomerOrderStatus.AWAITING_PAYMENT.equals(order.getStatus())
                        || CustomerOrderStatus.PAYMENT_APPROVED.equals(order.getStatus())
        ) {
            moveItemsFromOrderToBranch(order);
            switch (order.getStatus()) {
                case PAYMENT_APPROVED:
                    //send emails to customer and admin
                    CustomerOrderDto orderDto = convertForCustomer(order);
                    Customer customer = customerService.findById(customderId);
                    notificationService.orderCancelCustomer(customer.getEmail(), customer.getFullName(), orderDto);
                    eshopCustomerOrderApi.customerOrderCancel(new OrderIdRequest(orderId, customderId));
                    //eshopCustomerOrderApi.setOrderStatus(order.getId(), CustomerOrderStatus.CANCELED_BY_CUSTOMER.name());
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
        List<PurchaseDto> purchaseDtos = new ArrayList<>();
        order.getPurchases().forEach(op -> {
            CommodityBranchDto cb = eshopCommodityApi.getCommodityBranch(op.getId().getBranchId());
            CommodityDto c = eshopCommodityApi.findCommodityById(cb.getCommodityId());
            purchaseDtos.add(PurchaseDto
                    .builder()
                    .name(c.getName())
                    .overview(c.getOverview())
                    .shortDescription(c.getShortDescription())
                    .type(c.getType().getName())
                    .commodityId(c.getId())
                    .dateOfCreation(c.getDateOfCreation())
                    .price(cb.getPrice())
                    .amount(cb.getAmount())
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
                .purchases(purchaseDtos)
                .totalPrice(purchaseDtos.stream().mapToDouble(p -> p.getAmount() * p.getPrice()).sum())
                .build();
    }

    CustomerOrderDto convertForCustomer(CustomerOrder order) {
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

}
