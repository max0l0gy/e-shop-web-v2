<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:theme code="webRoot" var="webRoot"/>
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
<div class="titsonfire-customized-section">
    <div class="titsonfire-customized-section-text">
        <div class="mdl-typography--font-light mdl-typography--display-1-color-contrast">Customised by you, for you
        </div>
        <p class="mdl-typography--font-light">
            Put the stuff that you care about right on your home screen: the latest news, the weather or a stream of
            your recent photos.
            <br>
            <a href="" class="titsonfire-link mdl-typography--font-light">Customise your phone</a>
        </p>
    </div>
    <div class="titsonfire-customized-section-image"></div>
</div>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">More from Store</div>
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