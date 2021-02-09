package ru.maxmorev.restful.eshop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static ru.maxmorev.restful.eshop.config.PrefixRedirectAuthenticationSuccessHandler.REQUESTED_URL_BEFORE_LOGIN;

@Slf4j
@Controller
public class AdminWebController {

    @GetMapping(path = {"/security/in/"})
    public String securityPage(HttpServletRequest request, Model uiModel) {
        String referrer = request.getHeader("Referer");
        log.info("-- headers : {}", Collections.list(request.getHeaderNames()));
        if (referrer != null) {
            request.getSession().setAttribute(REQUESTED_URL_BEFORE_LOGIN, referrer);
        }
        return "customer/login";
    }

}
