package ru.maxmorev.restful.eshop.web;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.services.CommodityDtoService;
import ru.maxmorev.restful.eshop.services.CustomerService;
import ru.maxmorev.restful.eshop.services.ShoppingCartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class StaticWebController extends CommonWebController {

    public StaticWebController(@Autowired ShoppingCartService shoppingCartService,
                                  @Autowired CommodityDtoService commodityDtoService,
                                  @Autowired CustomerService customerService) {
        super(shoppingCartService, customerService, commodityDtoService);
    }
    //TODO add path variable for static pages
    @SneakyThrows
    @GetMapping(path = {"/about"})
    public String getShoppingCart(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        addCommonAttributesToModel(uiModel);
        addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        log.info("Listing shopping cart");
        return "static/about";
    }
}
