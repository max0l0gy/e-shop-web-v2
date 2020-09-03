package ru.maxmorev.restful.eshop.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommodityGridDto {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<CommodityDto> commodityData;

}
