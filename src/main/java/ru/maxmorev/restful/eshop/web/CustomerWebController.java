package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;
import ru.maxmorev.restful.eshop.rest.response.CustomerInfoDto;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerWebController {

    private final OrderPurchaseService orderPurchaseService;
    private final CommonWebController commonWebController;

    @GetMapping(path = {"/customer/account/create/", "/customer/account/create/{from}"})
    public String createAccount(
            @PathVariable(name = "from", required = false) Optional<String> from,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        from.ifPresent(s -> uiModel.addAttribute("fromPage", s));
        return "customer/createAccount";
    }

    @GetMapping(path = {"/customer/account/update/"})
    public String updateAccount(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        String id = commonWebController.getAuthenticationCustomerId();
        log.info("customerId {}", id);
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        CustomerInfoDto customerDTO = commonWebController.customerService.findByEmail(id).map(CustomerInfoDto::of).get();
        uiModel.addAttribute("customer", customerDTO.toJsonString());
        uiModel.addAttribute(
                "orders",
                orderPurchaseService
                        .findOrderListForCustomer(customerDTO.getId()));
        return "customer/updateAccount";
    }

    @GetMapping(path = {"/customer/account/reset-password"})
    public String resetPassword(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        return "customer/reset-password";
    }

    @GetMapping(path = {"/customer/account/reset-password/email/{email}/code/{code}"})
    public String resetPasswordLink(
            @PathVariable("email") String email,
            @PathVariable("code") String code,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        uiModel.addAttribute("email", email);
        uiModel.addAttribute("code", code);
        return commonWebController
                .customerService
                .findByEmail(email)
                .map(customer -> getResetPasswordTemplateName(code, customer))
                .orElse("customer/update-password-link-error");
    }

    private String getResetPasswordTemplateName(String code, CustomerDto customer) {
        try {
            if (UUID.fromString(code).equals(customer.getResetPasswordCode()))
                return "customer/update-password-link";
            else
                return "customer/update-password-link-error";
        } catch (IllegalArgumentException uuidException) {
            log.error("Error in code", uuidException);
            return "customer/update-password-link-error";
        }

    }
}
