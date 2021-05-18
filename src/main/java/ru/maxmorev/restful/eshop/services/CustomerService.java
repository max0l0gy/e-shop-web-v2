package ru.maxmorev.restful.eshop.services;


import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;
import ru.maxmorev.restful.eshop.rest.request.UpdatePasswordRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Customer createCustomerAndVerifyByEmail(Customer customer);

    void update(Customer customer);

    Customer updateInfo(CustomerInfo i);

    Customer findById(Long id);

    Optional<Customer> findByEmail(String email);

    CustomerVerify verify(CustomerVerify customerVerify);

    Optional<CustomerDto> generateResetPasswordCode(String email);

    Optional<CustomerDto> updatePassword(UpdatePasswordRequest updatePasswordRequest);

}
