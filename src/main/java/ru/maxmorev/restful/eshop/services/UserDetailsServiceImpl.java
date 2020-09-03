package ru.maxmorev.restful.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerApi;

@Component(value = "userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EshopCustomerApi eshopCustomerApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return eshopCustomerApi.findByEmail(username);
    }
}
