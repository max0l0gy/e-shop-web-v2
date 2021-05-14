package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.maxmorev.restful.eshop.domain.CustomerAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class CustomerDto {
    private static  ObjectMapper mapper = new ObjectMapper();
    private static final String emailRFC2822 = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    protected Long id;

    @Email(regexp = emailRFC2822, message = "{validation.email.format}")
    @NotBlank(message = "{validation.customer.email}")
    private String email;

    @NotBlank(message = "{validation.customer.fullName}")
    private String fullName;

    @NotBlank(message = "{validation.customer.country}")
    private String country;

    @NotBlank(message = "{validation.customer.postcode}")
    private String postcode;

    @NotBlank(message = "{validation.customer.city}")
    private String city;
    
    @NotBlank(message = "{validation.customer.address}")
    private String address;

    private Date dateOfCreation;

    private String password;

    private Boolean verified = false;

    private String verifyCode;

    private Long shoppingCartId;

    private UUID resetPasswordCode;

    private Collection<CustomerAuthority> authorities;

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
