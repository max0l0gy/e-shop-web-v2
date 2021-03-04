package ru.maxmorev.restful.eshop.feignclient.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Capture {
    private String id;
    private CaptureStatus status;
    private Amount amount;
    @JsonProperty("final_capture")
    private boolean finalCapture;
    @JsonProperty("seller_receivable_breakdown")
    private SellerReceivableBreakdown sellerReceivableBreakdown;
    private List<Link> links;
}
