package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.time.Instant;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddedPaymentResponse {
    public static final String PENDING = "pending";
    public static final String WAITING_FOR_CAPTURE = "waiting_for_capture";
    public static final String SUCCEEDED = "succeeded";

    private String id;
    private String status;
    private Boolean paid;
    private Amount amount;
    private Confirmation confirmation;
    @JsonProperty("created_at")
    private Instant createdAt;

}
