package ru.maxmorev.restful.eshop.feignclient.domain.portfolio;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class PortfolioDto implements Comparable<PortfolioDto> {
    protected Long id;

    private String name;

    private String description;

    private String shortDescription;

    protected List<String> images = new ArrayList<>();

    @Override
    public int compareTo(PortfolioDto portfolioDto) {
        return getId().compareTo(portfolioDto.getId());
    }
}
