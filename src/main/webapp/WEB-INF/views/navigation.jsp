<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:theme code="webRoot" var="webRoot"/>
<spring:message code="header_text" var="headerText"/>
<spring:message code="label_about" var="labelAbout"/>
<spring:message code="label_contacts" var="labelContacts"/>
<spring:url value="${webRoot}/logout" var="logoutUrl"/>
<spring:url value="${webRoot}/commodities" var="commoditiesUrl"/>
<spring:url value="${webRoot}/commodities/type" var="commoditiesByTypeUrl"/>
<spring:url value="${webRoot}/shopping/cart/" var="shoppingCartUrl"/>
<spring:url value="${webRoot}/customer/account/update/" var="accountUrl"/>
<spring:url value="${webRoot}/static/about" var="aboutUrl"/>
<spring:url value="${webRoot}/portfolios" var="portfoliosUrl"/>
<spring:url value="${webRoot}/static/artist-statement" var="statementUrl"/>
<spring:url value="${webRoot}/static/contacts" var="contactsUrl"/>
<spring:url value="${webRoot}/static/please-read" var="pleaseReadUrl"/>
<spring:url value="${webRoot}/commodities/archive" var="commoditiesArchiveUrl"/>
<spring:url value="${webRoot}/static/order-custom" var="orderCustomUrl"/>

<div class="titsonfire-drawer mdl-layout__drawer">
  <span class="mdl-layout-title">
    <img class="titsonfire-logo-image" src="${webRoot}/images/logo.png">
  </span>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link tab-store" href="${commoditiesUrl}">Store</a>
        <a class="mdl-navigation__link tab-portfolios" href="${portfoliosUrl}">Portfolio</a>
        <a class="mdl-navigation__link tab-artist-statement" href="${statementUrl}">Artist Statement&#160;/&#160;CV</a>
        <a class="mdl-navigation__link tab-about" href="${aboutUrl}">About</a>
        <a class="mdl-navigation__link tab-contacts" href="${contactsUrl}">Contacts</a>
        <div class="titsonfire-drawer-separator"></div>
        <span class="mdl-navigation__link" href="${commoditiesUrl}">STORE</span>
        <a class="mdl-navigation__link tab-please-read" href="${pleaseReadUrl}"><b>Please Read</b></a>
        <c:if test="${not empty types}">
            <c:forEach items="${types}" var="type">
                <c:if test="${not empty currentType}">
                    <c:if test="${currentType.name == type.name}">
                        <a class="mdl-navigation__link is-active tab-store-${type.name}"
                           href="${commoditiesByTypeUrl}/${type.name}">${type.name}</a>
                    </c:if>
                    <c:if test="${currentType.name != type.name}">
                        <a class="mdl-navigation__link tab-store-${type.name}"
                           href="${commoditiesByTypeUrl}/${type.name}">${type.name}</a>
                    </c:if>
                </c:if>
                <c:if test="${empty currentType}">
                    <a class="mdl-navigation__link tab-store-${type.name}"
                       href="${commoditiesByTypeUrl}/${type.name}">${type.name}</a>
                </c:if>
            </c:forEach>
        </c:if>
        <a class="mdl-navigation__link tab-archive" href="${commoditiesArchiveUrl}">Archive</a>
        <a class="mdl-navigation__link tab-order-custom" href="${orderCustomUrl}">Order a custom</a>
        <a class="mdl-navigation__link tab-shopping-cart" href="${shoppingCartUrl}">
            <div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav">shopping_cart</div>
        </a>
        <a class="mdl-navigation__link tab-account" href="${accountUrl}">
            <div class="material-icons">account_circle</div>
        </a>
        <div class="titsonfire-drawer-separator"></div>
        <span class="mdl-navigation__link tab-portfolios" href="${portfoliosUrl}">Portfolio</span>
        <c:forEach items="${portfoliosNews}" var="portfolio">
            <c:if test="${not empty currentPortfolio}">
                <c:if test="${currentPortfolio.id == portfolio.id}">
                    <a class="mdl-navigation__link is-active tab-portfolio-${portfolio.id}"
                       href="${portfoliosUrl}/${portfolio.id}">${portfolio.name}</a>
                </c:if>
                <c:if test="${currentPortfolio.id != portfolio.id}">
                    <a class="mdl-navigation__link tab-portfolio-${portfolio.id}"
                       href="${portfoliosUrl}/${portfolio.id}">${portfolio.name}</a>
                </c:if>
            </c:if>
            <c:if test="${empty currentPortfolio}">
                <a class="mdl-navigation__link tab-portfolio-${portfolio.id}"
                   href="${portfoliosUrl}/${portfolio.id}">${portfolio.name}</a>
            </c:if>
        </c:forEach>
    </nav>
</div>

