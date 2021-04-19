<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:theme code="webRoot" var="webRoot" />
<spring:url value="${webRoot}/commodity" var="showCommodityUrl"/>
<spring:message code="label_price" var="labelPrice"/>
<spring:message code="label_colors" var="labelColors"/>
<spring:message code="label_sizes" var="labelSizes"/>

<div class="titsonfire-more-section">
    <div class="titsonfire-section-title">STORE</div>

     <div class="mdl-grid">

        <div class="mdl-cell mdl-cell--6-col mdl-cell--6-col-tablet mdl-cell--6-col-phone mdl-shadow--1dp" style="width:350px;height:350px;align:center;">
            <a href="https://titsonfire.store/web/commodities/type/T-SHiRT"><img src="https://www2.lunapic.com/editor/premade/crop-circle.gif" height="345px"></a>
        </div>

        <div class="mdl-cell mdl-cell--6-col mdl-cell--6-col-tablet mdl-cell--6-col-phone mdl-shadow--1dp" style="width:350px;height:350px;align:center;">
            <a href="https://titsonfire.store/web/commodities/type/T-SHiRT"><img src="https://www2.lunapic.com/editor/premade/crop-circle.gif" height="345px"></a>
        </div>

        <div class="mdl-cell mdl-cell--6-col mdl-cell--6-col-tablet mdl-cell--6-col-phone mdl-shadow--1dp" style="width:350px;height:350px;align:center;">
             <a href="https://titsonfire.store/web/commodities/type/T-SHiRT"><img src="https://www2.lunapic.com/editor/premade/crop-circle.gif" height="345px"></a>
        </div>

     </div>


</div>