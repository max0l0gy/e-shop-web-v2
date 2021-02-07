package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartDto  extends JsonMappedValue {
    private Long id;
    private List<ShoppingCartSetDto> shoppingSet;

    public int getItemsAmount() {
        return shoppingSet != null ? shoppingSet.stream().mapToInt(ShoppingCartSetDto::getAmount).sum() : 0;
    }

    public double getTotalPrice() {
        return shoppingSet != null ? shoppingSet.stream().mapToDouble(scs->scs.getAmount()*scs.getBranch().getPrice()).sum(): 0.0d;
    }

    private String getCurrencyCode() {
        return shoppingSet != null ? shoppingSet.stream().findFirst().map(scs->scs.getBranch().getCurrency()).orElse(""):"";
    }

}
