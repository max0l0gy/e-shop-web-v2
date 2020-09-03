package ru.maxmorev.restful.eshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityAttribute {

    private Long id;
    private String name;
    private AttributeDataType dataType;
    private String measure;
    private Set<CommodityAttributeValue> values = new HashSet<>();

}
