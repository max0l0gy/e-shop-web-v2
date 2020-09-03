package ru.maxmorev.restful.eshop.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class ServiceExseptionSuppressor {

    public static <T> Optional<T> suppress(Supplier<? extends T> action ){
        try{
            return Optional.ofNullable(action.get());
        }catch (Exception ex){
            log.error("ServiceExseptionSuppressor --> {}", ex);
        }
        return Optional.empty();
    }

}
