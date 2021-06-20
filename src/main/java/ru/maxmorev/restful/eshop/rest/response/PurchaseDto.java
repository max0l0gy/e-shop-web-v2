package ru.maxmorev.restful.eshop.rest.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;
import ru.maxmorev.restful.eshop.rest.request.AttributeDto;

import java.util.Currency;
import java.util.Date;
import java.util.List;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PurchaseDto extends JsonMappedValue {
    Integer amount;//amount of items
    Long branchId;//branchId
    Long commodityId;
    String name;
    String shortDescription;
    String overview;
    Date dateOfCreation;
    String type;
    List<String> images;
    Float price; //price for 1 item in branch
    Currency currency; //current price currency
    List<AttributeDto> attributes;
}
