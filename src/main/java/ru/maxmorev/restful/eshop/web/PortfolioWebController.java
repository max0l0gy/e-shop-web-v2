package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.feignclient.PortfolioApi;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioResponse;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PortfolioWebController {

    private final CommonWebController commonWebController;
    private final PortfolioApi portfolioApi;

    @GetMapping(path = "/portfolios")
    public String portfolios(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        log.info("Loading portfolios");
        PortfolioResponse<List<PortfolioDto>> portfolioResponse = portfolioApi.list();
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        uiModel.addAttribute("portfolios", portfolioResponse.getData());
        log.info("Portfolios size: " + portfolioResponse.getData().size());
        return "portfolios";
    }

}
