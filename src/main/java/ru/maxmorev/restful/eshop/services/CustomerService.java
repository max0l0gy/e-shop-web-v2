package ru.maxmorev.restful.eshop.services;


import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;
import ru.maxmorev.restful.eshop.rest.request.UpdatePasswordRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;

import java.util.Optional;

public interface CustomerService {

    CustomerDto createCustomerAndVerifyByEmail(Customer customer);

    void update(Customer customer);

    CustomerDto updateInfo(CustomerInfo i);

    CustomerDto findById(Long id);

    Optional<CustomerDto> findByEmail(String email);

    CustomerVerify verify(CustomerVerify customerVerify);

    Optional<CustomerDto> generateResetPasswordCode(String email);

    Optional<CustomerDto> updatePassword(UpdatePasswordRequest updatePasswordRequest);

}
