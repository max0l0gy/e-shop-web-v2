package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CapturedOrderRefundStatus {
    public static final String CANCELLED = "CANCELLED";
    public static final String PENDING = "PENDING";
    public static final String COMPLETED = "COMPLETED";
    public static final String UNDEFINED = "UNDEFINED";
    String status = UNDEFINED;

    public boolean isCompleted() {
        return status.equals(COMPLETED);
    }

    public boolean isCanceled() {
        return status.equals(CANCELLED);
    }

    public boolean isUndefined() {
        return status.equals(UNDEFINED);
    }

    public boolean isPending() {
        return status.equals(PENDING);
    }
}
