package ru.maxmorev.restful.eshop.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class PrefixRedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public static String REQUESTED_URL_BEFORE_LOGIN = "requested_url_before_login";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Value("${web.root}")
    private String webRoot;
    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        log.info("--- PrefixRedirectAuthenticationSuccessHandler ");
        String targetUrl = request.getRequestURL().toString();
        log.info("targetUrl is {}", targetUrl);
        log.info("webRoot is {}", webRoot);

        HttpSession session = request.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
        log.info("SPRING_SECURITY_SAVED_REQUEST_KEY : {}", savedRequest);
        if(savedRequest != null) {
            log.info("savedRequest.getRedirectUrl() is {}", savedRequest.getRedirectUrl());
            response.sendRedirect(savedRequest.getRedirectUrl());
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
