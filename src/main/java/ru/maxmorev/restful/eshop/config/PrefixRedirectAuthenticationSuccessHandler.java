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
    public static String REQUESTED_URL_BEFORE_LOGIN = "requested_url_before_login";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Value("${web.root}")
    private String webRoot;
    @Value("${web.host}")
    private String webHost;

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
        String requestedUrl = request.getSession().getAttribute(REQUESTED_URL_BEFORE_LOGIN).toString();
        log.info("REQUESTED_URL_BEFORE_LOGIN : {}", requestedUrl);
        if (requestedUrl != null) {
            String redirectedUrlWithWebPrefix = requestedUrl.replace(webHost, webRoot);
            log.info("{} -> {}", requestedUrl, redirectedUrlWithWebPrefix);
            redirectStrategy.sendRedirect(request, response, redirectedUrlWithWebPrefix);
        }
    }
}
