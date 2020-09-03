package ru.maxmorev.restful.eshop.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.maxmorev.restful.eshop.validation.AttributeDuplicationValues;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Currency;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityBranchDto {
    @NotNull(message = "{branch.id.notNull}")
    private Long id;
    @NotNull(message = "{branch.commodityId.notNull}")
    private Long commodityId;
    @NotNull(message = "{validation.commodity.amount.NotNull.message}")
    @Min(value = 1, message = "{validation.commodity.amount.min.message}")
    private Integer amount;
    @NotNull(message = "{validation.commodity.price.NotNull.message}")
    @Positive(message = "{validation.commodity.price.gt.zero}")
    private Float price;
    private String currency;
    @NotNull(message = "{validation.commodity.propertyValues.NotNull.message}")
    @Size(min=1, message = "{validation.commodity.propertyValues.size.message}")
    @AttributeDuplicationValues(message = "{validation.commodity.attribute.duplication.values}")
    private  List<@Valid AttributeDto>  attributes;

    @JsonIgnore
    @AssertTrue(message = "{validation.commodity.currencyCode.exist}")
    public boolean isValidCurrencyCode() {
        if (Currency.getAvailableCurrencies().stream().anyMatch(c -> c.getCurrencyCode().equals(currency))) {
            return true;
        }
        return false;
    }

}
