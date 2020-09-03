package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityType {

    protected Long id;
    private int version;
    @NotBlank(message = "{validation.CommodityType.name.NotBlank.message}")
    private String name;
    @NotBlank(message = "{validation.CommodityType.description.NotBlank.message}")
    @Size(min = 8, max = 128, message = "{validation.CommodityType.description.size.message}")
    private String description;

}

