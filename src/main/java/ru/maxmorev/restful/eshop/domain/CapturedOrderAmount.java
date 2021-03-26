package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CapturedOrderAmount {
    private String currencyCode;
    private String value;
}
