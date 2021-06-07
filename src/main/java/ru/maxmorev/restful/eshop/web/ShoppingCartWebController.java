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
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShoppingCartWebController {

    private final OrderPurchaseService orderPurchaseService;
    private final CommonWebController commonWebController;
    private final YoomoneyApi yoomoneyApi;

    @GetMapping(path = {"/shopping/cart/"})
    public String getShoppingCart(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        log.info("Listing shopping cart");
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        return "shopping/cart";
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
                    .setAmount(BigDecimal.valueOf(scFromCookie.getTotalPrice()))
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
        return "shopping/proceedToCheckout";
    }

    @SneakyThrows
    @GetMapping(path = {"/payment/completed/orderid/{orderId}"})
    public String paymentCompleted(
            @PathVariable("orderId") Long orderId,
            @PathVariable("paymentId") String paymentId,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        log.info("check conditions");
        String customerEmail = commonWebController.getAuthenticationCustomerId();
        commonWebController.addCommonAttributesToModel(uiModel);
        return commonWebController.customerService
                .findByEmail(customerEmail)
                .map(customer -> orderPurchaseService.findOrder(orderId, customer.getId()))
                .map(order -> processOrderAndPaymentConditions(uiModel, order))
                .orElse("shopping/payment-error")
                ;
    }

    private String processOrderAndPaymentConditions(Model uiModel, CustomerOrderDto order) {
        uiModel.addAttribute("customerOrder", order);
        EmbeddedPaymentResponse payment = yoomoneyApi.getPayment(order.getPaymentID()).getData();
        uiModel.addAttribute("payment", payment);
        log.info("payment paid: {}", payment.getPaid());
        if (payment.getPaid()) {
            return "shopping/payment-completed";
        } else {
            return "shopping/payment-not-paid";
        }
    }


}
