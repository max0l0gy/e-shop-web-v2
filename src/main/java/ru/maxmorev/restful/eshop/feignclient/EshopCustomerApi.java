package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;

@FeignClient(name = "eshop-customer-api", url = "${external.customerApi.url}")
public interface EshopCustomerApi {

    @RequestMapping(path = "/customer/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Customer createCustomer(@RequestBody Customer customer);

    @RequestMapping(path = "/admin/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Customer createAdmin(@RequestBody Customer customer);

    @RequestMapping(path = "/update/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    Customer updateCustomer(@RequestBody CustomerInfo customer);

    @RequestMapping(path = "/customer/verify/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    CustomerVerify verifyCustomer(@RequestBody CustomerVerify customerVerify);

    @RequestMapping(path = "/customer/email/{email}", method = RequestMethod.GET)
    Customer findByEmail(@PathVariable(name = "email") String email);

    @RequestMapping(path = "/customer/id/{id}", method = RequestMethod.GET)
    Customer findById(@PathVariable(name = "id") Long id);

}
