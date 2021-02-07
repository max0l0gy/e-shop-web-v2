package ru.maxmorev.restful.eshop.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PrefixRedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
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
        log.info("webRoot is {}", webRoot);
        log.info("request.getRequestURL() is {}", targetUrl);
        log.info("request.getContextPath() is {}", request.getContextPath());
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
