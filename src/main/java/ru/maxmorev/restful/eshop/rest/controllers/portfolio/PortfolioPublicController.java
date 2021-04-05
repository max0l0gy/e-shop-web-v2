package ru.maxmorev.restful.eshop.rest.controllers.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.feignclient.PortfolioApi;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioResponse;
import ru.maxmorev.restful.eshop.rest.Constants;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioPublicController {

    private final PortfolioApi portfolioApi;

    @ResponseBody
    @GetMapping(Constants.REST_PUBLIC_URI + "portfolios")
    public PortfolioResponse<List<PortfolioDto>> portfolios() {
        return portfolioApi.list();
    }

    @ResponseBody
    @GetMapping(Constants.REST_PUBLIC_URI + "portfolios/{id}")
    public PortfolioResponse<PortfolioDto> portfolioById(@PathVariable("id") Long id) {
        return portfolioApi.find(id);
    }

}
