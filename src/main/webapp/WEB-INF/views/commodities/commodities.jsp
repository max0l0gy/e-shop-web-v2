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

<script type="text/javascript">
function showWearAttributes(id, sizes, colors){
    var content='Sizes:';
    sizes.forEach(function(size){ content+= showSizeElement(size);});
    content += '<br/>';
    content += 'Colors:';
    colors.forEach(function(color){ content+= showColorElement(color);});
    $('#attribute-container-'+id).empty();
    $('#attribute-container-'+id).append(content);
}

function showAttributes(id, attributes){

    var content="";
    attributes.forEach(function(prop){
        content += showCommodityAttribute(prop);
    });
    $('#attribute-container-'+id).empty();
    $('#attribute-container-'+id).append(content);

}

$(document).ready(function () {
    activateTab('tab-store');
    var commoditiesJsonStr = '${commodities}';
    var res = commoditiesJsonStr.replace(/\n/g, " ");//cleanup string
    var commodities = JSON.parse(res);
    commodities.forEach( function(commodity) {
        //process commodity
        const attributes = commodity.branches[0].attributes;
        var notWearAttributes = getNotWearAttributes(attributes);
        if(notWearAttributes.length>0){

            showAttributes(commodity.id, notWearAttributes);
        }else{
            //show attributes for wear

            var colors = [];
            var sizes = [];
            commodity.branches.forEach( function(branch){

                var wearAttributes = branch.attributes;

                wearAttributes.forEach(function(a){
                                        if(a.name=="color"){
                                            if( !colors.includes(a.value) ){
                                                colors.push(a.value);
                                            }
                                        }
                                        if(a.name=="size"){
                                             if( !sizes.includes(a.value) ){
                                                sizes.push(a.value);
                                             }
                                        }
                                    });

            });
            showWearAttributes(commodity.id, sizes, colors);
        }
    }
    );
});
</script>

<div class="titsonfire-more-section">
<div class="titsonfire-navigation-subsection mdl-tabs mdl-js-tabs mdl-js-ripple-effect">
    <div class="mdl-tabs__tab-bar">
        <a href="#t-shirts-panel" class="mdl-tabs__tab is-active">T-Shirts</a>
        <a href="#ceramics-panel" class="mdl-tabs__tab">Ceramics</a>
        <a href="#canvases-panel" class="mdl-tabs__tab">Canvases</a>
        <a href="#archive-panel" class="mdl-tabs__tab">Archive</a>
        <a href="#order-custom-panel" class="mdl-tabs__tab">Order a custom</a>
    </div>

    <div class="mdl-tabs__panel is-active" id="t-shirts-panel">
        <c:if test="${not empty commodities}">
                <div class="titsonfire-card-container mdl-grid">
                    <c:forEach items="${commodities}" var="commodity">
                        <div class="mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-card mdl-shadow--3dp">
                            <div class="mdl-card__media">
                                <a href="${showCommodityUrl}/${commodity.id}"><img class="article-image" src="${commodity.images[0]}" border="0" alt=""/></a>
                            </div>
                            <div class="mdl-card__title">
                                <h2 class="mdl-card__title-text">${commodity.name}</h2>
                            </div>
                            <div class="mdl-card__supporting-text">
                                <span class="mdl-typography--font-light mdl-typography--subhead">${commodity.shortDescription}</span>
                                <div id="attribute-container-100">

                                </div>
                            </div>
                            <div class="mdl-card__actions mdl-card--border" style="height:50px">
                                <div class="portfolio-list-action">
                                    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent"
                                       href="${showCommodityUrl}/${commodity.id}">${labelPrice} &#160;
                                        ${commodity.branches[0].price} ${commodity.branches[0].currency}</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
        </c:if>
    </div>
    <div class="mdl-tabs__panel" id="ceramics-panel">
        <ul>
            <li>Ceramics</li>
            <li>Option</li>
        </ul>
    </div>
    <div class="mdl-tabs__panel" id="canvases-panel">
        <ul>
            <li>Canvases</li>
            <li>Daenerys</li>
        </ul>
    </div>
</div>

</div>