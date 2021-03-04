package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CapturedOrderStatus {
    public static final String COMPLETED = "COMPLETED";
    public static final String DECLINED = "DECLINED";
    public static final String PENDING = "PENDING";
    public static final String REFUNDED = "REFUNDED";
    public static final String UNDEFINED = "UNDEFINED";

    String status = UNDEFINED;

    public boolean isCompleted() {
        return status.equals(COMPLETED);
    }

    public boolean isDeclined() {
        return status.equals(DECLINED);
    }

    public boolean isUndefined() {
        return status.equals(UNDEFINED);
    }

    public boolean isRefunded() {
        return status.equals(REFUNDED);
    }

    public boolean isPending() {
        return status.equals(PENDING);
    }
}
