package ru.maxmorev.restful.eshop.rest.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UpdatePasswordRequest extends JsonMappedValue {
    @NotBlank
    private String newPassword;
    @NotBlank
    private String customerEmail;
    @NotNull
    private UUID resetPasswordCode;
}
