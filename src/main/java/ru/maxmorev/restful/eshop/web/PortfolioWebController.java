package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.services.PortfolioService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PortfolioWebController {

    private final CommonWebController commonWebController;
    private final PortfolioService portfolioService;

    @GetMapping(path = "/portfolios")
    public String portfolios(
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        log.info("Loading portfolios");
        List<PortfolioDto> portfolios = portfolioService.portfolios();
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        uiModel.addAttribute("portfolios", portfolios);
        log.info("Portfolios size: " + portfolios.size());
        return "portfolios";
    }

    @GetMapping(path = "/portfolios/{id}")
    public String portfolioById(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel
    ) {
        log.info("Loading portfolio id={}", id);
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        return portfolioService.findBy(id)
                .map(portfolioDto -> {
                    uiModel.addAttribute("portfolio", portfolioDto);
                    uiModel.addAttribute("currentPortfolio", portfolioDto);
                    return "portfolio";
                })
                .orElse("itemNotFound");
    }

}
