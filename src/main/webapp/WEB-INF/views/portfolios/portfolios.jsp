<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:theme code="webRoot" var="webRoot" />
<spring:url value="${webRoot}/portfolios" var="itemUrl"/>

<script type="text/javascript">

$(document).ready(function () {
    activateTab('tab-portfolios');

});
</script>

<div class="titsonfire-customized-section">
    <div class="titsonfire-customized-section-text">
        <div class="mdl-typography--font-light mdl-typography--display-1-color-contrast">Write something about portfolio
        </div>
        <p class="mdl-typography--font-light">
            Why it should be here
            <br>
            <a href="" class="titsonfire-link mdl-typography--font-light">I like you portfolio</a>
        </p>
    </div>
    <div class="titsonfire-customized-section-image"></div>
</div>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">New from Store</div>
    <c:if test="${not empty portfolios}">
        <div class="titsonfire-card-container mdl-grid">
            <c:forEach items="${portfolios}" var="portfolio">
                <div class="mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-card mdl-shadow--3dp">
                    <div class="mdl-card__media">
                        <a href="${itemUrl}/${portfolio.id}"><img class="article-image" src="${portfolio.images[0]}" border="0" alt=""/></a>
                    </div>
                    <div class="mdl-card__title">
                        <h2 class="mdl-card__title-text">${portfolio.name}</h2>
                    </div>
                    <div class="mdl-card__supporting-text">
                        <span class="mdl-typography--font-light mdl-typography--subhead">${portfolio.shortDescription}</span>
                        <div id="attribute-container">

                        </div>
                    </div>
                    <div class="mdl-card__actions mdl-card--border" style="height:50px">
                        <div class="portfolio-list-action">
                            <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent" href="${itemUrl}/${portfolio.id}">
                            Open
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>