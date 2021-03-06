package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.request.CommodityInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityDto extends CommodityInfoDto {

    private List<CommodityBranchDto> branches;

    @JsonIgnore
    public String getLastImageUri() {
        int size = getImages().size();
        if (size > 0) {
            return getImages().get(size - 1);
        } else {
            return "";
        }
    }

}
