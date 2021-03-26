package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.response.CheckAuthResponse;
import ru.maxmorev.restful.eshop.services.CustomerService;

@RestController
@RequiredArgsConstructor
public class BasicAuthCheckController {

    private final CustomerService cs;

    public String getAuthenticationCustomerId() {
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

    @GetMapping(path = Constants.REST_MANAGER_URI + "checkAuth")
    @ResponseBody
    public CheckAuthResponse createAttribute() {
        //to prevent duplicated properties
        String email = getAuthenticationCustomerId();
        return new CheckAuthResponse(cs.findByEmail(email).get().getAuthorities());
    }


}
