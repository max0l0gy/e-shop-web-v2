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
<spring:url value="${webRoot}/logout" var="logoutUrl"/>
<spring:url value="${webRoot}/commodities/" var="commoditiesUrl"/>
<spring:url value="${webRoot}/commodities/type" var="commoditiesByTypeUrl"/>
<spring:url value="${webRoot}/shopping/cart/" var="shoppingCartUrl"/>
<spring:url value="${webRoot}/customer/account/update/" var="accountUrl"/>
<spring:url value="${webRoot}/about" var="aboutUrl"/>

<div class="titsonfire-header mdl-layout__header mdl-layout__header--waterfall">
 <div class="mdl-layout__header-row">
   <span class="titsonfire-title mdl-layout-title">
     <img class="titsonfire-logo-image" src="${webRoot}/images/logo.png">
   </span>
   <!-- Add spacer, to align navigation to the right in desktop -->
   <div class="titsonfire-header-spacer mdl-layout-spacer"></div>
   <div class="titsonfire-search-box mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-textfield--full-width">
     <label class="mdl-button mdl-js-button mdl-button--icon" for="search-field">
       <i class="material-icons">search</i>
     </label>
     <div class="mdl-textfield__expandable-holder">
       <input class="mdl-textfield__input" type="text" id="search-field">
     </div>
   </div>
   <!-- Navigation -->
   <div class="titsonfire-navigation-container">
     <nav class="titsonfire-navigation mdl-navigation">
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="${commoditiesUrl}">Store</a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Portfolio</a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Artist Statement</a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="${aboutUrl}">About</a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Contacts</a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="${shoppingCartUrl}"><div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav" >shopping_cart</div></a>
       <a class="mdl-navigation__link mdl-typography--text-uppercase" href="${accountUrl}"><div class="material-icons" >account_circle</div></a>
     </nav>
   </div>
   <span class="titsonfire-mobile-title mdl-layout-title">
     <img class="titsonfire-logo-image" src="${webRoot}/images/logo.png">
   </span>
   <button class="titsonfire-more-button mdl-button mdl-js-button mdl-button--icon mdl-js-ripple-effect" id="more-button">
     <i class="material-icons">more_vert</i>
   </button>
   <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right mdl-js-ripple-effect" for="more-button">
     <li class="mdl-menu__item">About</li>
   </ul>
 </div>
</div>