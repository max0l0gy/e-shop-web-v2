package ru.maxmorev.restful.eshop.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RemoveFromCartRequest {
    @NotNull(message = "{validation.shopping.cart.id.NotNull}")
    private Long shoppingCartId;
    @NotNull(message = "{validation.shopping.cart.branch.id.NotNull}")
    private Long branchId;
    @NotNull
    private Integer amount;
}
