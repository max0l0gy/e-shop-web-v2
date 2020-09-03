package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerApi;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;
import ru.maxmorev.restful.eshop.util.ServiceExseptionSuppressor;

import java.util.Optional;

@Slf4j
@Service("customerService")
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final EshopCustomerApi eshopCustomerApi;
    private final NotificationService notificationService;

    @Override
    public Customer createCustomerAndVerifyByEmail(Customer customer) {
        Customer created = eshopCustomerApi.createCustomer(customer);
        notificationService.emailVerification(
                created.getEmail(),
                created.getFullName(),
                created.getVerifyCode());
        return created;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return ServiceExseptionSuppressor.suppress(() -> eshopCustomerApi.findByEmail(email));
    }

    @Override
    public void update(Customer customer) {
        eshopCustomerApi.updateCustomer(customer);
    }

    @Override
    public CustomerVerify verify(CustomerVerify customerVerify) {
        return eshopCustomerApi.verifyCustomer(customerVerify);
    }

    @Override
    public Customer findById(Long id) {
        return eshopCustomerApi.findById(id);
    }

    @Override
    public Customer updateInfo(CustomerInfo i) {
        return eshopCustomerApi.updateCustomer(i);
    }
}
