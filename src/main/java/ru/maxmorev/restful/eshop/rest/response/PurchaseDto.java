package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Value;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;
import ru.maxmorev.restful.eshop.rest.request.AttributeDto;

import java.util.Currency;
import java.util.Date;
import java.util.List;

@Value
@Builder
public class PurchaseDto extends JsonMappedValue {
    private Integer amount;//amount of items
    private Long branchId;//branchId
    private Long commodityId;
    private String name;
    private String shortDescription;
    private String overview;
    private Date dateOfCreation;
    private String type;
    private List<String> images;
    private Float price; //price for 1 item in branch
    private Currency currency; //current price currency
    private List<AttributeDto> attributes;

}
