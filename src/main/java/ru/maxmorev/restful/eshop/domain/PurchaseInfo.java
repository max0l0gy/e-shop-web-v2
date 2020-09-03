package ru.maxmorev.restful.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseInfo {
    @NotNull
    private Integer amount;
    @NotNull
    private Float price;
    @NotBlank
    private String commodityName;
    @NotBlank
    private String commodityImageUri;
}
