package ru.maxmorev.restful.eshop.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.maxmorev.restful.eshop.domain.CustomerAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAuthResponse {

    private Collection<CustomerAuthority> authorities;

}
