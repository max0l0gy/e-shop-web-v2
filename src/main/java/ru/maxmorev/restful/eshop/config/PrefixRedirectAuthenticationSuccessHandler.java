package ru.maxmorev.restful.eshop.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
public class PrefixRedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public static String REQUESTED_URL_BEFORE_LOGIN = "requested_url_before_login";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Value("${web.root}")
    private String webRoot;
    @Value("${web.host}")
    private String webHost;
    @Value("${security.defaultRedirectPage}")
    private String defaultRedirectPage;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        log.info("----------- PrefixRedirectAuthenticationSuccessHandler ----------");
        String targetUrl = request.getRequestURL().toString();
        log.info("targetUrl : {}", targetUrl);
        log.info("webRoot : {}", webRoot);
        log.info("webHost : {}", webHost);
        String requestedUrl = getRedirectUrlFromCookies(request);
        log.info("REQUESTED_URL_BEFORE_LOGIN : {}", requestedUrl);
        redirectStrategy.sendRedirect(request, response, requestedUrl);
    }

    private String getRedirectUrlFromCookies(HttpServletRequest request) {
        String requestedUrl =  Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REQUESTED_URL_BEFORE_LOGIN))
                .findFirst()
                .map(Cookie::getValue).orElse(webHost + defaultRedirectPage);
        log.info("Requested URL from Cookie: {}", requestedUrl);
        String redirectedUrlWithWebPrefix = requestedUrl.replace(webHost, webRoot);
        log.info("{} - adding prefix -> {}", requestedUrl, redirectedUrlWithWebPrefix);
        return redirectedUrlWithWebPrefix;
    }
}
