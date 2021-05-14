package ru.maxmorev.restful.eshop.rest.request;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UpdatePasswordRequest extends JsonMappedValue {
    @NotBlank
    private String newPassword;
    @NotNull
    private Long customerId;
    @NotBlank
    private UUID resetPasswordCode;
}
