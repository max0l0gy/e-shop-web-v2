package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends CustomerInfo implements UserDetails {

    @NotBlank(message = "{validation.customer.password}")
    private String password;
    private String verifyCode;
    private Date dateOfCreation;
    private Boolean verified = false;
    private Long shoppingCartId;
    private Set<CustomerAuthority> authorities;
    private UUID resetPasswordCode;

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return verified;
    }

    @Override
    public boolean isAccountNonLocked() {
        return verified;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return verified;
    }

    @Override
    public boolean isEnabled() {
        return verified;
    }

    @Builder
    public Customer(Long id,
                    @NotBlank(message = "{validation.customer.email}") String email,
                    @NotBlank(message = "{validation.customer.fullName}") String fullName,
                    @NotBlank(message = "{validation.customer.country}") String country,
                    @NotBlank(message = "{validation.customer.postcode}") String postcode,
                    @NotBlank(message = "{validation.customer.city}") String city,
                    @NotBlank(message = "{validation.customer.address}") String address,
                    @NotBlank(message = "{validation.customer.password}") String password) {
        super(id, email, fullName, country, postcode, city, address);
        this.password = password;
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

}
