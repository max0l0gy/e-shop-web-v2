<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:theme code="webRoot" var="webRoot" />

<script type="text/javascript">

$(document).ready(function () {
    activateTab('tab-portfolios');
});
</script>

<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">Portfolio&#160;<b>${portfolio.name}</b></div>
    <div class="titsonfire-card-container mdl-grid">
        <!-- portfolio card -->
        <!-- ITEM IMAGES -->
        <div class="mdl-cell mdl-cell--12-col mdl-card mdl-shadow--1dp">
            <div class="images" id="images">
                <img id="mainImage" class="item-image" src="${portfolio.images[0]}"/>
                <div class="images-navi mdl-grid">
                    <c:forEach items="${currentPortfolio.images}" var="image" varStatus="loop">
                        <c:if test="${loop.index==0}">
                            <img  id="img-nav" src="${image}" onClick="mark(this, '${image}');" class="circleImgSelection"/>
                        </c:if>
                        <c:if test="${loop.index>0}">
                            <img id="img-nav" src="${image}" onClick="mark(this, '${image}');" class="circleImgUnselected"/>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- END: ITEM IMAGES -->
        <div class="mdl-cell mdl-cell--12-col mdl-card mdl-shadow--2dp">
            <div class="mdl-grid titsonfire-card-content">
                <div class="mdl-cell mdl-cell--12-col mdl-typography--font-light mdl-typography--subhead">
                    <strong>Portfolio</strong>&#160;<span>${currentPortfolio.shortDescription}</span>
                </div>
                    <h3 class="mdl-cell mdl-cell--12-col mdl-typography--headline commodity-name">Portfolio&#160; ${currentPortfolio.name}</h3>
                    <div class="mdl-cell mdl-cell--6-col mdl-typography--font-light no-padding">
                        <span class="mdl-typography--font-light mdl-typography--subhead">${currentPortfolio.description}</span>
                    </div>
                    <div class="mdl-cell mdl-cell--6-col">
                         <img class="article-image" src="${currentPortfolio.images[0]}" width="200px" border="0" alt=""/>
                    </div>
            </div>
        </div>
        <!--end: portfolio card-->
    </div>
</div>

<script type="text/javascript">
initGallery();
var hammertime = Hammer(document.getElementById('mainImage'));
hammertime.on("swipeleft", function(event) {
        swipeLeftAction();
});
hammertime.on("swiperight", function(event) {
        swipeRightAction();
});
</script>