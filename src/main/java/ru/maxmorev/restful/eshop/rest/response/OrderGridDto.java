package ru.maxmorev.restful.eshop.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;

import java.util.List;

@Getter
@Builder
public class OrderGridDto {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<CustomerOrderDto> orderData;

}
