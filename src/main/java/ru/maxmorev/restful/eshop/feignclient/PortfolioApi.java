package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioResponse;

import java.util.List;

@FeignClient(name = "eshop-portfolio-api", url = "${external.portfolio.url}")
public interface PortfolioApi {

    @GetMapping("/api/v1/portfolios")
    PortfolioResponse<List<PortfolioDto>> list();

    @PostMapping("/api/v1/portfolios")
    PortfolioResponse<PortfolioDto> add(@RequestBody PortfolioDto portfolioToSave);

    @PutMapping("/api/v1/portfolios")
    PortfolioResponse<PortfolioDto> update(@RequestBody PortfolioDto portfolioDto);

    @GetMapping("/api/v1/portfolios/{id}")
    PortfolioResponse<PortfolioDto> find(@PathVariable("id") Long id);
}
