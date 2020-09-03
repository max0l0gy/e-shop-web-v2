package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Purchase implements Serializable {

    private PurchaseId id;

    private PurchaseInfo purchaseInfo;

    protected Purchase() {
    }

    public Purchase(Long branchId, CustomerOrder customerOrder, PurchaseInfo purchaseInfo) {
        this.id = new PurchaseId(branchId, customerOrder.getId());
        this.purchaseInfo = purchaseInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        Purchase purchase = (Purchase) o;
        return getId().equals(purchase.getId()) &&
                getPurchaseInfo().equals(purchase.getPurchaseInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPurchaseInfo());
    }
}
