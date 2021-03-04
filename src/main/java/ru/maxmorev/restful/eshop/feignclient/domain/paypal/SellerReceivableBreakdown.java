package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerReceivableBreakdown {
    @JsonProperty("gross_amount")
    private Amount grossAmount;
    @JsonProperty("paypal_fee")
    private Amount paypalFee;
    @JsonProperty("net_amount")
    private Amount netAmount;
    
}
