package ru.maxmorev.restful.eshop.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.maxmorev.restful.eshop.rest.JsonMapped;
import ru.maxmorev.restful.eshop.rest.response.CommodityTypeDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityInfoDto {

    @NotNull(message = "{branch.commodityId.notNull}")
    private Long id;
    @NotBlank(message = "{validation.commodity.name.NotBlank.message}")
    @Size(min = 8, max = 256, message = "{validation.commodity.name.size.message}")
    private String name;
    @NotBlank(message = "{validation.commodity.shortDescription.NotBlank.message}")
    @Size(min = 16, max = 256, message = "{validation.commodity.shortDescription.size.message}")
    private String shortDescription;
    @NotBlank(message = "{validation.commodity.overview.NotBlank.message}")
    @Size(min = 64, max = 2048, message = "{validation.commodity.overview.size.message}")
    private String overview;
    @PastOrPresent(message = "{validation.commodity.dateOfCreation.pastOrPresent}")
    private Date dateOfCreation;
    private CommodityTypeDto type;
    @Size(min = 1, message = "{validation.commodity.images.size.message}")
    private List<String> images;

}
