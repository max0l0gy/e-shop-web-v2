package ru.maxmorev.restful.eshop.rest.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordRequest  extends JsonMappedValue {
    @NotBlank
    private String customerEmail;
}
