<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:message code="application_name" var="appName"/>
<spring:message code="label_help" var="labelHelp"/>
<spring:message code="label_privacy" var="labelPrivacy"/>


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
    <a class="titsonfire-link titsonfire-link-menu mdl-typography--font-light" id="version-dropdown">
      Store
      <i class="material-icons">arrow_drop_up</i>
    </a>
    <ul class="mdl-menu mdl-js-menu mdl-menu--top-left mdl-js-ripple-effect" for="version-dropdown">
      <li class="mdl-menu__item">T-Shirts</li>
      <li class="mdl-menu__item">Ceramics</li>
      <li class="mdl-menu__item">Canvases</li>
      <li class="mdl-menu__item">Archive</li>
      <li class="mdl-menu__item">Order a custom</li>
      <li class="mdl-menu__item">Please Read</li>
    </ul>
    <a class="titsonfire-link titsonfire-link-menu mdl-typography--font-light" id="developers-dropdown">
      Portfolio
      <i class="material-icons">arrow_drop_up</i>
    </a>
    <ul class="mdl-menu mdl-js-menu mdl-menu--top-left mdl-js-ripple-effect" for="developers-dropdown">
      <li class="mdl-menu__item">Dracula Series</li>
      <li class="mdl-menu__item">Fonts</li>
      <li class="mdl-menu__item">Sketchbook Pieces</li>
      <li class="mdl-menu__item">Gucciholes story</li>
      <li class="mdl-menu__item">Illustrations</li>
    </ul>
    <a class="titsonfire-link mdl-typography--font-light" href="">Instagram</a>
    <a class="titsonfire-link mdl-typography--font-light" href="">About</a>
    <a class="titsonfire-link mdl-typography--font-light" href="">Privacy Policy</a>
  </div>
</footer>


