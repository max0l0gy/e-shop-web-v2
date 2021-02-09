package ru.maxmorev.restful.eshop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static ru.maxmorev.restful.eshop.config.PrefixRedirectAuthenticationSuccessHandler.REQUESTED_URL_BEFORE_LOGIN;

@Slf4j
@Controller
public class AdminWebController {

    @GetMapping(path = {"/security/in/"})
    public String securityPage(HttpServletRequest request, Model uiModel) {
        log.info("------------------------ securityPage ------------------------");
        HttpSession session = request.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        log.info("SPRING_SECURITY_SAVED_REQUEST : {}", savedRequest);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("savedRequest.getRedirectUrl() is {}", redirectUrl);
            request.getSession().setAttribute(REQUESTED_URL_BEFORE_LOGIN, redirectUrl);
        }
        return "customer/login";
    }

}
