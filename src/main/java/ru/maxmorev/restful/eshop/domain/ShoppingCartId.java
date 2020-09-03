package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ShoppingCartId implements Serializable {

    Long branchId;
    Long shoppingCartId;

    protected ShoppingCartId() {
    }

    public ShoppingCartId(Long branchId, Long shoppingCartId) {
        if (branchId == null) throw new IllegalArgumentException("branchId cannot be null");
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
        this.branchId = branchId;
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartId)) return false;
        ShoppingCartId that = (ShoppingCartId) o;
        return branchId.equals(that.branchId) &&
                shoppingCartId.equals(that.shoppingCartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, shoppingCartId);
    }
}
