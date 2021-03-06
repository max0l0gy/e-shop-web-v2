     <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <%@ page isELIgnored="false" %>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
     <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <spring:theme code="webRoot" var="webRoot" />
    <spring:message code="header_text" var="headerText"/>
    <spring:message code="label_about" var="labelAbout"/>
    <spring:message code="label_contacts" var="labelContacts"/>
    <spring:url var="logoutUrl" value="${webRoot}/logout" />
    <spring:url value="${webRoot}/commodity/type" var="showCommoditiesByTypeUrl"/>
    <spring:url value="${webRoot}/shopping/cart/" var="shoppingCartUrl"/>
    <spring:url value="${webRoot}/customer/account/update/" var="accountUrl"/>
    <spring:url value="${webRoot}/about" var="aboutUrl"/>

    <header class="mdl-layout__header mdl-layout__header--waterfall portfolio-header">
        <div class="mdl-layout__header-row portfolio-logo-row">
            <span class="mdl-layout__title">
                <div class="portfolio-logo">△</div>
                <span class="mdl-layout__title">${headerText}</span>
            </span>
        </div>
        <div class="mdl-layout__header-row portfolio-navigation-row mdl-layout--large-screen-only">
            <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
                <c:if test="${not empty types}">
                    <c:forEach items="${types}" var="type">
                    <c:if test="${not empty currentType}">
                       <c:if test="${currentType.name == type.name}">
                        <a class="mdl-navigation__link is-active" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                       </c:if>
                       <c:if test="${currentType.name != type.name}">
                       <a class="mdl-navigation__link" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                       </c:if>
                    </c:if>
                    <c:if test="${empty currentType}">
                    <a class="mdl-navigation__link" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                    </c:if>
                    </c:forEach>

                </c:if>
                <a class="tab-about mdl-navigation__link" href="${aboutUrl}">${labelAbout}</a>
                <a class="mdl-navigation__link" href="#contacts">${labelContacts}</a>
                <a class="tab-shopping-cart mdl-navigation__link" href="${shoppingCartUrl}"><div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav" >shopping_cart</div></a>
                <a class="tab-account-update mdl-navigation__link" href="${accountUrl}"><div class="material-icons">account_circle</div></a>
            </nav>
        </div>
    </header>
    <div class="mdl-layout__drawer mdl-layout--small-screen-only">
        <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
                <c:if test="${not empty types}">
                    <c:forEach items="${types}" var="type">
                    <c:if test="${not empty currentType}">
                       <c:if test="${currentType.name == type.name}">
                        <a class="mdl-navigation__link is-active" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                       </c:if>
                       <c:if test="${currentType.name != type.name}">
                       <a class="mdl-navigation__link" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                       </c:if>
                    </c:if>
                    <c:if test="${empty currentType}">
                    <a class="mdl-navigation__link" href="${showCommoditiesByTypeUrl}/${type.name}">${type.name}</a>
                    </c:if>
                    </c:forEach>

                </c:if>
            <a class="tab-about mdl-navigation__link" href="${aboutUrl}">${labelAbout}</a>
            <a class="mdl-navigation__link" href="#contacts">${labelContacts}</a>
            <a class="tab-shopping-cart mdl-navigation__link" href="${shoppingCartUrl}"><div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav" >shopping_cart</div></a>
            <a class="tab-account-update mdl-navigation__link" href="${accountUrl}"><div class="material-icons">account_circle</div></a>
        </nav>
    </div>