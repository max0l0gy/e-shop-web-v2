package ru.maxmorev.restful.eshop.services;


import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;

import java.util.Optional;

public interface CustomerService {

    Customer createCustomerAndVerifyByEmail(Customer customer);

    void update(Customer customer);

    Customer updateInfo(CustomerInfo i);

    Customer findById(Long id);

    Optional<Customer> findByEmail(String email);

    CustomerVerify verify(CustomerVerify customerVerify);

}
