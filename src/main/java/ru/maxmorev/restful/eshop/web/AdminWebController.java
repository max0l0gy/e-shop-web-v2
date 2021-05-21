package ru.maxmorev.restful.eshop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maxmorev.restful.eshop.annotation.ShoppingCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static ru.maxmorev.restful.eshop.config.PrefixRedirectAuthenticationSuccessHandler.REQUESTED_URL_BEFORE_LOGIN;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminWebController {
    private final CommonWebController commonWebController;

    @GetMapping(path = {"/security/in/"})
    public String securityPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue(value = ShoppingCookie.SHOPPiNG_CART_NAME, required = false) Cookie cartCookie,
            Model uiModel) {
        log.info("------------------------ securityPage ------------------------");
        HttpSession session = request.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        log.info("SPRING_SECURITY_SAVED_REQUEST : {}", savedRequest);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("savedRequest.getRedirectUrl() is {}", redirectUrl);
            request.getSession().setAttribute(REQUESTED_URL_BEFORE_LOGIN, redirectUrl);
                setRequestedPageToCookie(redirectUrl, response);
        }
        commonWebController.addCommonAttributesToModel(uiModel);
        commonWebController.addShoppingCartAttributesToModel(cartCookie, response, uiModel);
        return "customer/login";
    }

    private void setRequestedPageToCookie(String requestPage, HttpServletResponse response) {
        Cookie requestedUrlCookie = new Cookie(REQUESTED_URL_BEFORE_LOGIN, requestPage);
        requestedUrlCookie.setComment("Requested page for usability of our web shop UI. Thank you.");
        requestedUrlCookie.setMaxAge(60 * 60 * 24);//1 days in seconds
        requestedUrlCookie.setPath("/");
        //newCartCookie.setDomain();
        log.info("SETTING COOKIE");
        response.addCookie(requestedUrlCookie);
    }

}
