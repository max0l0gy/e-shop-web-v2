package ru.maxmorev.restful.eshop.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDto {
    @NotNull
    private String name;
    @NotNull
    private String value;
    private String measure;
}
