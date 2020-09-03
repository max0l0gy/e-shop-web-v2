package ru.maxmorev.restful.eshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderGrid {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<CustomerOrder> orderData;

}
