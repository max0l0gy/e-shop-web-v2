package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartSet {

    private ShoppingCartId id;
    private PurchaseInfo purchaseInfo;
    private int amount;

    public ShoppingCartSet(Long branchId, ShoppingCart shoppingCart, PurchaseInfo purchaseInfo) {
        this.id = new ShoppingCartId(branchId, shoppingCart.getId());
        this.purchaseInfo = purchaseInfo;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartSet)) return false;
        ShoppingCartSet that = (ShoppingCartSet) o;
        return getId().equals(that.getId()) &&
                getPurchaseInfo().equals(that.getPurchaseInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPurchaseInfo());
    }
}
