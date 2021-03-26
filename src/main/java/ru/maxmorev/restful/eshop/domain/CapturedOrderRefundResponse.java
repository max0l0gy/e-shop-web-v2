package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CapturedOrderRefundResponse {
    private String id;
    private CapturedOrderRefundStatus status;
}
