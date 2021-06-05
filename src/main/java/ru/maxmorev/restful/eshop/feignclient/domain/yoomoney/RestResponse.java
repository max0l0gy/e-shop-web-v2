package ru.maxmorev.restful.eshop.feignclient.domain.yoomoney;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestResponse<T> {
    private String status;
    T data;

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<T>()
                .setData(data)
                .setStatus("success");
    }
}
