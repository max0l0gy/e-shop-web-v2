package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.rest.response.CustomerDTO;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
        CustomerDTO customerDTO = commonWebController.customerService.findByEmail(id).map(CustomerDTO::of).get();
        uiModel.addAttribute("customer", customerDTO.toJsonString());
        uiModel.addAttribute(
                "orders",
                orderPurchaseService
                        .findOrderListForCustomer(customerDTO.getId()));
        return "customer/updateAccount";
    }
}
