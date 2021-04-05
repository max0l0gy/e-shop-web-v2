package ru.maxmorev.restful.eshop.rest.controllers.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.feignclient.PortfolioApi;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioResponse;
import ru.maxmorev.restful.eshop.rest.Constants;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioManagerController {
    private final PortfolioApi portfolioApi;

    @ResponseBody
    @PostMapping(Constants.REST_MANAGER_URI + "portfolios")
    public PortfolioResponse<PortfolioDto> create(@RequestBody @Valid PortfolioDto createPortfolio) {
        log.info("POST -> portfolio");
        return portfolioApi.add(createPortfolio);
    }

    @ResponseBody
    @PutMapping(path = Constants.REST_MANAGER_URI + "portfolios")
    public PortfolioResponse<PortfolioDto> update(@RequestBody @Valid PortfolioDto portfolioToUpdate) {
        log.info("PUT -> portfolio");
        return portfolioApi.update(portfolioToUpdate);
    }
}
