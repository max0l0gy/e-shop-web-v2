package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.request.CommodityInfoDto;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartSetDto {
    private Integer amount;
    private CommodityBranchDto branch;
    private CommodityInfoDto commodityInfo;
}
