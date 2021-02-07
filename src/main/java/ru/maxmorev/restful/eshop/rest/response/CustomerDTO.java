package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.JsonMappedValue;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO extends JsonMappedValue {
    private Long id;
    private String email;
    private String fullName;
    private String country;
    private String postcode;
    private String city;
    private String address;

    public static CustomerDTO of(CustomerInfo info) {
        return CustomerDTO.builder()
                .id(info.getId())
                .email(info.getEmail())
                .fullName(info.getFullName())
                .country(info.getCountry())
                .city(info.getCity())
                .address(info.getAddress())
                .postcode(info.getPostcode())
                .build();
    }

}
