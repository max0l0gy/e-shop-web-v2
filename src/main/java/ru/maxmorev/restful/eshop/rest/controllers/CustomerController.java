package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.domain.CustomerInfo;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.request.CustomerVerify;
import ru.maxmorev.restful.eshop.rest.request.UpdatePasswordRequest;
import ru.maxmorev.restful.eshop.rest.response.CustomerDTO;
import ru.maxmorev.restful.eshop.rest.response.CustomerDto;
import ru.maxmorev.restful.eshop.services.CustomerService;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MessageSource messageSource;

    private String getAuthenticationCustomerId() {
        String id = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
                id = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
                id = (String) authentication.getPrincipal();
        return id;
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + "customer/", method = RequestMethod.POST)
    @ResponseBody
    public CustomerDTO createCustomer(@RequestBody @Valid Customer customer, Locale locale) {
        log.info("Customer : {}", customer);
        return CustomerDTO.of(customerService.createCustomerAndVerifyByEmail(customer));
    }

    @RequestMapping(path = Constants.REST_CUSTOMER_URI + "update/", method = RequestMethod.PUT)
    @ResponseBody
    public CustomerDTO updateCustomer(@RequestBody @Valid CustomerInfo customer, Locale locale) {
        log.info("Customer update : {}", customer);
        String id = getAuthenticationCustomerId();
        log.info("Authentication id = {}", id);
        /* user can update only self data */
        if (!id.equals(customer.getEmail()))
            throw new BadCredentialsException("Not Authenticated");
        Customer findByEmail = customerService.updateInfo(customer);
        return CustomerDTO.of(findByEmail);
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + "customer/verify/", method = RequestMethod.POST)
    @ResponseBody
    public CustomerVerify verifyCustomer(@RequestBody @Valid CustomerVerify customerVerify, Locale locale) {
        return customerService.verify(customerVerify);
    }

    @SneakyThrows
    @GetMapping(path = Constants.REST_MANAGER_URI + "customer/id/{id}")
    @ResponseBody
    public CustomerDTO findCustomer(@PathVariable(name = "id") Long id, Locale locale) {
        return CustomerDTO.of(customerService.findById(id));
    }

    @GetMapping(path = Constants.REST_PUBLIC_URI + "customer/reset-password-code/email/{email}")
    @ResponseBody
    public CustomerDto generateResetPasswordCode(@PathVariable(name = "email") String email, Locale locale) {
        log.info("generateResetPasswordCode email : {}", email);
        return customerService
                .generateResetPasswordCode(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("customer.error.notFound.email", new Object[]{email}, locale)));
    }

    @PostMapping(path = Constants.REST_PUBLIC_URI + "customer/update-password")
    @ResponseBody
    public CustomerDto updatePassword(@RequestBody
                                      @Valid UpdatePasswordRequest updatePasswordRequest,
                                      Locale locale) {
        return customerService
                .updatePassword(updatePasswordRequest)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageSource.getMessage("customer.error.notFound",
                                new Object[]{updatePasswordRequest.getCustomerEmail()}, locale)
                ));
    }



}
