<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:theme code="webRoot" var="webRoot" />
<spring:theme code="styleSheet" var="app_css" />
<spring:theme code="styleSheetApp" var="app_css_app" />
<spring:url value="${webRoot}/${app_css}" var="app_css_urlapp_css_url" />
<spring:url value="${webRoot}/${app_css_app}" var="app_css_app_url" />
<spring:url value="${webRoot}/images" var="app_img_url" />
<spring:message code="application_name" var="app_name" htmlEscape="false"/>
<!doctype html>
<!--
  Created by maxim.morev on 05/09/19 for TiTSonFiRE.store
  USING
  Material Design Lite
  Copyright 2019. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Victoria Romanovich Artist, creator, founder of TitsOnFire">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>TiTSONFiRE.STORE</title>
    <!-- Page styles -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.purple-pink.min.css" />
    <link rel="stylesheet" href="${webRoot}/styles.css">
    <!-- Page scripts -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/hammerjs/2.0.8/hammer.min.js"></script>
    <script src="${webRoot}/scripts/common.js"></script>
    <script src="${webRoot}/scripts/application.js"></script>
    <script type="text/javascript">
            const URL_SERVICES = "${webRoot}<%=request.getContextPath().equals("")?"":request.getContextPath()%>/rest/api";
            var SHOPPING_CART_ITEMS_AMOUNT = ${shoppingCartItemsAmount};
    </script>
    <style>
    #view-source {
      position: fixed;
      display: block;
      right: 0;
      bottom: 0;
      margin-right: 40px;
      margin-bottom: 40px;
      z-index: 900;
    }
    </style>
  </head>
  <body>
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
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
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Store</a>
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Portfolio</a>
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Artist Statement / CV</a>
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">About</a>
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href="">Contacts</a>
              <a class="mdl-navigation__link mdl-typography--text-uppercase" href=""><div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav" >shopping_cart</div></a>
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

      <div class="titsonfire-drawer mdl-layout__drawer">
        <span class="mdl-layout-title">
          <img class="titsonfire-logo-image" src="${webRoot}/images/logo.png">
        </span>
        <nav class="mdl-navigation">
          <a class="mdl-navigation__link" href="">Store</a>
          <a class="mdl-navigation__link" href="">Portfolio</a>
          <a class="mdl-navigation__link" href="">Artist Statement / CV</a>
          <a class="mdl-navigation__link" href="">About</a>
          <a class="mdl-navigation__link" href="">Contacts</a>
          <div class="titsonfire-drawer-separator"></div>
          <span class="mdl-navigation__link" href="">STORE</span>
          <a class="mdl-navigation__link" href=""><b>Please Read</b></a>
          <a class="mdl-navigation__link" href="">T-Shirts</a>
          <a class="mdl-navigation__link" href="">Ceramics</a>
          <a class="mdl-navigation__link" href="">Canvases</a>
          <a class="mdl-navigation__link" href="">Archive</a>
          <a class="mdl-navigation__link" href="">Order a custom</a>
          <a class="mdl-navigation__link" href=""><div class="material-icons mdl-badge mdl-badge--overlap shopping-cart-nav" >shopping_cart</div></a>
          <div class="titsonfire-drawer-separator"></div>
          <span class="mdl-navigation__link" href="">Portfolio</span>
          <a class="mdl-navigation__link" href="">Dracula series</a>
          <a class="mdl-navigation__link" href="">Fonts</a>
          <a class="mdl-navigation__link" href="">Sketchbook Pieces</a>
          <a class="mdl-navigation__link" href="">Gucciholes story</a>
          <a class="mdl-navigation__link" href="">Illustrations</a>
        </nav>
      </div>

      <div class="titsonfire-content mdl-layout__content">
        <a name="top"></a>
        <!-- body container -->
        <tiles:insertAttribute name="body"/>
        <!-- end: body container -->
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
      </div>
    </div>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script type="text/javascript">
       window.addEventListener('load', function() {
               showShoppingCartIconDataBadge(SHOPPING_CART_ITEMS_AMOUNT);
       });
    </script>
  </body>
</html>
