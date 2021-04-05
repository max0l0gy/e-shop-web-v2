package ru.maxmorev.restful.eshop.feignclient.domain.portfolio;

import lombok.Data;

@Data
public class PortfolioResponse<T> {
    String status;
    T data;

    public PortfolioResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }
}
