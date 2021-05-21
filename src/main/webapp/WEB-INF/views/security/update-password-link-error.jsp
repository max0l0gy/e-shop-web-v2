<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/" var="app_url"/>
<spring:url value="${webRoot}/security/in/" var="loginUrl"/>
<spring:url value="${webRoot}/customer/account/reset-password" var="resetPasswordUrl"/>

<div class="titsonfire-more-section">
    <div class="titsonfire-section-title">TiTS ON FIRE SECURITY</div>
    <div id="reset-password" class="titsonfire-card-container mdl-grid">
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">UPDATE PASSWORD LINK ERROR</h2>
            </div>
            <div class="mdl-card__supporting-text">
                You are here because your code has expired
                or there is no user with email address <b>${email}</b> in our store.
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <!-- end: security card -->
    </div>
</div>