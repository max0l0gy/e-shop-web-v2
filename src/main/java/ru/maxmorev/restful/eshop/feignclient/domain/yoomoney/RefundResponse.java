package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RefundResponse {
    public static final String CANCELED = "canceled";
    public static final String SUCCEEDED = "succeeded";

    private String id;
    private String status;
    private Amount amount;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("payment_id")
    private String paymentId;

}
