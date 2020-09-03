package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityImage {
    private String uri;
    private Integer width;
    private Integer height;
    private Short imageOrder;
}
