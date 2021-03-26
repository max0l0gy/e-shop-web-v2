package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundResponse extends DomainJson {
    private String id;
    private RefundStatus status;

    @SneakyThrows
    public static RefundResponse fromJsonString(String json) {
        return MAPPER.readValue(json, RefundResponse.class);
    }
}
