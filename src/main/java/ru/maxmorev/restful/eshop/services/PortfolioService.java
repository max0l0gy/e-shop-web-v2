package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.feignclient.PortfolioApi;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioDto;
import ru.maxmorev.restful.eshop.feignclient.domain.portfolio.PortfolioResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioApi portfolioApi;

    public List<PortfolioDto> portfolios() {
        PortfolioResponse<List<PortfolioDto>> portfolios = portfolioApi.list();
        return portfolios.getData() != null ?
                portfolios.getData() : Collections.emptyList();
    }

    public Optional<PortfolioDto> findBy(Long id) {
        return Optional.ofNullable(portfolioApi.find(id).getData());
    }

}
