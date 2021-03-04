package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Purchase  {
    @JsonProperty("reference_id")
    private String referenceId;
    private Amount amount;
    private String description;
    private Payments payments;
}
