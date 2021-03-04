package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

public enum HttpMethod {
    OPTIONS,
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    TRACE,
    CONNECT,
    PATCH,
    OTHER;

    private HttpMethod() {
    }
}