package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.maxmorev.restful.eshop.annotation.PaymentProvider;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.feignclient.YoomoneyApi;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.EmbeddedPaymentResponse;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.PaymentRequest;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RestResponse;
import ru.maxmorev.restful.eshop.rest.request.PaymentInitialRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerOrderDto;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartDto;
import ru.maxmorev.restful.eshop.services.CurrencyRateService;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShoppingCartWebController {

    private final OrderPurchaseService orderPurchaseService;
    private final CommonWebController commonWebController;
    private final YoomoneyApi yoomoneyApi;
    private final CurrencyRateService currencyRateService;

    @GetMapping(path = {"/shopping/cart/"})
    public String getShoppingCart(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        log.info("Listing shopping cart");
        commonWebController.addCommonAttributesToModel(uiModel);
        addShoppingCartAttributes(response, cartCookie, uiModel);
        return "shopping/cart";
    }

    private void addShoppingCartAttributes(HttpServletResponse response, Cookie cartCookie, Model uiModel) {
        ShoppingCartDto shoppingCartDto = commonWebController.mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        uiModel.addAttribute("shoppingCart", shoppingCartDto);
        uiModel.addAttribute("shoppingCartTotalPrice", shoppingCartDto.getTotalPrice());
        uiModel.addAttribute("shoppingCartTotalPriceRub", convertUsdToRubles(shoppingCartDto));
    }

    private Optional<BigDecimal> convertUsdToRubles(ShoppingCartDto shoppingCartDto) {
        return currencyRateService.getCurrencyRateContainer().convertUsdToRub(shoppingCartDto.getTotalPrice());
    }

    @SneakyThrows
    @GetMapping(path = {"/shopping/cart/checkout/"})
    public String proceedToCheckout(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        //merge shopping cart after login
        ShoppingCartDto scFromCookie = commonWebController.mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        if (scFromCookie.getShoppingSet().isEmpty()) {
            //redirect to cart
            response.sendRedirect("/shopping/cart");
        }
        //create transaction order and hold items for 10 minutes
        String id = commonWebController.getAuthenticationCustomerId();
        commonWebController.customerService.findByEmail(id).ifPresent(authCustomer -> {
            CustomerOrder order = orderPurchaseService.createOrderFor(authCustomer);
            PaymentRequest paymentRequest = new PaymentRequest()
                    .setIdempotenceKey(String.valueOf(order.getId()))
                    .setAmount(scFromCookie.getTotalPrice())
                    .setDescription("Order No. " + order.getId());
            RestResponse<EmbeddedPaymentResponse> yoomoneyPayment = yoomoneyApi.initial(paymentRequest);
            if (null != yoomoneyPayment.getData()) {
                log.info("yoomoney status : {}", yoomoneyPayment.getData().getStatus());
                PaymentInitialRequest paymentInitialRequest = new PaymentInitialRequest()
                        .setPaymentID(yoomoneyPayment.getData().getId())
                        .setPaymentProvider(PaymentProvider.Yoomoney.name())
                        .setOrderId(order.getId());
                order = orderPurchaseService
                        .paymentInitial(paymentInitialRequest)
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                uiModel.addAttribute("confirmationToken", yoomoneyPayment.getData().getConfirmation().getConfirmationToken());
                //TODO implement payment initial process for OrderPurchaseService issue https://github.com/users/max0l0gy/projects/1#card-62528080
            }
            uiModel.addAttribute("orderId", order.getId());
            uiModel.addAttribute("customer", authCustomer);
            uiModel.addAttribute("customerOrder", order);

        });
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.getShoppingCartFromCookie(cartCookie, response);
        addShoppingCartAttributes(response, cartCookie, uiModel);
        return "shopping/proceedToCheckout";
    }

    @SneakyThrows
    @GetMapping(path = {"/payment/completed/orderid/{orderId}"})
    public String paymentCompleted(
            @PathVariable("orderId") Long orderId,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        log.info("check conditions");
        String customerEmail = commonWebController.getAuthenticationCustomerId();
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.getShoppingCartFromCookie(cartCookie, response);
        return commonWebController.customerService
                .findByEmail(customerEmail)
                .map(customer -> {
                    uiModel.addAttribute("customer", customer);
                    return customer;
                })
                .map(customerDto ->
                        Optional.ofNullable(
                                orderPurchaseService.confirmPaymentOrder(orderId, customerDto.getId())
                                        .orElseGet(() -> orderPurchaseService.findOrder(orderId, customerDto.getId()).orElse(null))
                        )
                )
                .map(order -> processOrderAndPaymentConditions(uiModel, order))
                .orElse("shopping/payment-error")
                ;
    }


    private String processOrderAndPaymentConditions(Model uiModel, Optional<CustomerOrderDto> order) {
        return order
                .map(customerOrder -> checkOrderAndPayment(uiModel, customerOrder))
                .orElse("shopping/order-confirmation-error");

    }

    private String checkOrderAndPayment(Model uiModel, CustomerOrderDto customerOrder) {
        uiModel.addAttribute("customerOrder", customerOrder);
        EmbeddedPaymentResponse payment = yoomoneyApi.getPayment(customerOrder.getPaymentID()).getData();
        uiModel.addAttribute("payment", payment);
        log.info("payment paid: {}", payment.getPaid());
        if (payment.getPaid()) {
            return "shopping/payment-completed";
        } else {
            return "shopping/payment-not-paid";
        }
    }


}
