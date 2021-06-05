package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Confirmation {
    public static final String EMBEDDED = "embedded";
    private String type;
    @JsonProperty("confirmation_token")
    private String confirmationToken;

}
