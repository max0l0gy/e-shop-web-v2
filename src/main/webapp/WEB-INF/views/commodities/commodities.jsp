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
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">$TOR€</div>

    <div class="titsonfire-card-container mdl-grid">
         <div class="mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-card mdl-shadow--3dp">
             <div class="mdl-card__media">
                 <a href="https://titsonfire.store/web/commodity/100"><img class="article-image-circle" src="https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/9253ce113708491.602d47b9156b6.jpg" border="0" alt=""/></a>
             </div>
             <div class="mdl-card__title">
                 <h2 class="mdl-card__title-text">T-SHIRT</h2>
             </div>
             <div class="mdl-card__supporting-text">
                 <span class="mdl-typography--font-light mdl-typography--subhead">Handmade t-shirts with oil craft</span>
             </div>
             <div class="mdl-card__actions mdl-card--border" style="height:50px">
                 <div class="portfolio-list-action">
                     <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent"
                        href="https://titsonfire.store/web/commodity/100">OPEN</a>
                 </div>
             </div>
         </div>
    </div>

</div>