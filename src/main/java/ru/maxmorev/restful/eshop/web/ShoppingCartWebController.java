package ru.maxmorev.restful.eshop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartDto;
import ru.maxmorev.restful.eshop.services.CommodityDtoService;
import ru.maxmorev.restful.eshop.services.CustomerService;
import ru.maxmorev.restful.eshop.services.OrderPurchaseService;
import ru.maxmorev.restful.eshop.services.ShoppingCartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ShoppingCartWebController extends CommonWebController {

    private final OrderPurchaseService orderPurchaseService;

    public ShoppingCartWebController(ShoppingCartService shoppingCartService,
                                     CommodityDtoService commodityDtoService,
                                     OrderPurchaseService orderPurchaseService,
                                     CustomerService customerService) {
        super(shoppingCartService, customerService, commodityDtoService);
        this.orderPurchaseService = orderPurchaseService;
    }

    @GetMapping(path = {"/shopping/cart/"})
    public String getShoppingCart(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        log.info("Listing shopping cart");
        addCommonAttributesToModel(uiModel);
        mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        return "shopping/cart";
    }

    @GetMapping(path = {"/shopping/cart/checkout/"})
    public String proceedToCheckout(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) throws IOException {
        //merge shopping cart after login
        ShoppingCartDto scFromCookie = mergeShoppingCartFromCookieWithCustomerIfNeed(cartCookie, response, uiModel);
        if (scFromCookie.getShoppingSet().size() == 0) {
            //redirect to cart
            response.sendRedirect("/shopping/cart/");
        }

        //create transaction order and hold items for 10 minutes
        String id = getAuthenticationCustomerId();
        Customer authCustomer = customerService.findByEmail(id).get();
        CustomerOrder order = orderPurchaseService.createOrderFor(authCustomer);
        uiModel.addAttribute("orderId", order.getId());
        uiModel.addAttribute("customer", authCustomer);

        addCommonAttributesToModel(uiModel);
        return "shopping/proceedToCheckout";
    }


}
