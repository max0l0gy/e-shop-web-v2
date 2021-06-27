<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="application_name" var="appName"/>
<spring:message code="label_help" var="labelHelp"/>
<spring:message code="label_privacy" var="labelPrivacy"/>
<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/static/about/" var="aboutUrl"/>
<spring:url value="${webRoot}/static/privacy-policy" var="privacyUrl"/>
<spring:url value="https://www.instagram.com/titsonfire.artist" var="instaUrl"/>

<footer class="titsonfire-footer mdl-mega-footer">
    <div class="mdl-mega-footer--top-section">
        <div class="mdl-mega-footer--left-section">
            <button class="mdl-mega-footer--social-btn"></button>
            &nbsp;
            <button class="mdl-mega-footer--social-btn"></button>
            &nbsp;
            <button class="mdl-mega-footer--social-btn"></button>
        </div>
        <div class="mdl-mega-footer--right-section">
            <a class="mdl-typography--font-light" href="#top">
                Back to Top
                <i class="material-icons">expand_less</i>
            </a>
        </div>
    </div>

    <div class="mdl-mega-footer--middle-section">
        <p class="mdl-typography--font-light">TiTS on FiRE © 2020 Moscow</p>
        <p class="mdl-typography--font-light">Fire Wear With Fire</p>
    </div>

    <div class="mdl-mega-footer--bottom-section">
        <a class="titsonfire-link mdl-typography--font-light" target="_blank" href="${instaUrl}">Instagram</a>
        <a class="titsonfire-link mdl-typography--font-light" href="${aboutUrl}">About</a>
        <a class="titsonfire-link mdl-typography--font-light" href="${privacyUrl}">Privacy Policy</a>
    </div>
</footer>


