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
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
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
      <!-- header -->
      <tiles:insertAttribute name="header"/>
      <!-- end:header -->
      <!-- navigation -->
      <tiles:insertAttribute name="navigation"/>
      <!-- end:navigation -->
      <div class="titsonfire-content mdl-layout__content">
        <a name="top"></a>
        <!-- body container -->
        <tiles:insertAttribute name="body"/>
        <!-- end: body container -->
        <!-- footer -->
        <tiles:insertAttribute name="footer"/>
        <!-- end:footer -->
      </div>

       <!-- Toast place -->
       <div id="demo-toast-example" class="mdl-js-snackbar mdl-snackbar">
       <div class="mdl-snackbar__text"></div>
       <button class="mdl-snackbar__action" type="button"></button>
       </div>
       <!-- end Toast place -->

    </div>
    <script type="text/javascript">
       window.addEventListener('load', function() {
               showShoppingCartIconDataBadge(SHOPPING_CART_ITEMS_AMOUNT);
       });
    </script>
  </body>
</html>
