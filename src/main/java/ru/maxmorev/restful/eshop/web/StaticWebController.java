package ru.maxmorev.restful.eshop.web;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StaticWebController {

    private final CommonWebController commonWebController;
    private Map<String, String> pages;

    @PostConstruct
    public void initPages() {
        pages = ImmutableMap.of(
                "about", "static/about",
                "artist-statement", "static/artist-statement",
                "contacts", "static/contacts",
                "order-custom", "static/order-custom",
                "please-read", "static/please-read"
        );
    }

    @SneakyThrows
    @GetMapping(path = {"/static/{page-name}"})
    public String getStaticPage(
            @PathVariable("page-name") String pageName,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        log.info("Listing shopping cart");
        //TODO add page not found
        return pages.get(pageName);
    }
}
