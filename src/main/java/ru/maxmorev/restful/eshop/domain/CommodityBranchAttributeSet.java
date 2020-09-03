package ru.maxmorev.restful.eshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityBranchAttributeSet {

    protected Long id;
    private CommodityAttribute attribute;
    private CommodityAttributeValue attributeValue;

}
