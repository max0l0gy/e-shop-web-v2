<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:theme code="webRoot" var="webRoot" />
<spring:message code="label_buy" var="labelBuy"/>
<spring:message code="label_price" var="labelPrice"/>
<spring:message code="label_checkout" var="labelCheckout"/>
<spring:message code="label_add_to_basket" var="labelBasket"/>
<spring:message code="label_choose_props" var="labelChooseProps"/>
<spring:message code="label_color" var="labelColor"/>
<spring:message code="label_size" var="labelSize"/>
<spring:message code="label_amount" var="labelAmount"/>
<spring:message code="label_VendorCode" var="labelVendorCode"/>
<spring:url value="${webRoot}/shopping/cart/checkout/" var="proceedToCheckoutUrl"/>

<script type="text/javascript">
const shoppingCartId = ${ShoppingCartCookie};
const shoppingCart = ${ShoppingCartCookie};
const commodityId = ${commodity.id};
var AMOUNT;
var PRICE;
var purchaseInfo = {
    branchId: null,
    amount: 1,
    price: null,
    commodityName: "${commodity.name}",
    commodityImageUri: "${commodity.images[0]}"
};
var branches = [];
var shoppingCartObj;

function addToShoppingCart(){
    var branchId = branches[0].id;
    purchaseInfo.branchId = branchId;
    purchaseInfo.price = branches[0].price;
    addToShoppingCartSet(shoppingCartId, purchaseInfo, addToShoppingCartSuccess, addToShoppingCartFail);

}

function showAttributes(propertySet){
  var content="";
  propertySet.forEach(function(prop){
    content += prop.name + ": " + prop.value + " " + prop.measure + "<br/>";
  });
  $('#attribute-container').empty();
  $('#attribute-container').append(content);

};

function showAmount(branch){
    var content="${labelAmount}:&#160;"+branch.amount;
    $('#amount-container').empty();
    $('#amount-container').append(content);
}

function drawButtonProceed() {
    $('#btn-proceed').hide();
    if( shoppingCartObj != undefined) {
        if(shoppingCartObj.shoppingSet!=undefined && shoppingCartObj.shoppingSet.length>0) {
            $('#btn-proceed').show();
        }
    } else {
        $('#btn-proceed').hide();
    }

}

function loadCartSuccess(json){
    shoppingCartObj = json;
    drawButtonProceed();
}

$(document).ready(function () {
    getShoppingCart(shoppingCartId, loadCartSuccess);
    var branches_str = '${commodity.branches}';
    branches = JSON.parse(branches_str);
    var propertySet = branches[0].attributes;
    propertySet.sort(function(a,b){ return a.measure>b.measure});
    showAttributes(propertySet);
    showAmount(branches[0]);
    var btnAddToBasket = document.querySelector('#btn-add-to-basket');
    btnAddToBasket.addEventListener('click', addToShoppingCart);
    var btnProceed = document.querySelector('#btn-proceed');
    btnProceed.addEventListener('click', function() {
        window.location.href = "${proceedToCheckoutUrl}";
    } );

});
</script>

<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">${commodity.type.name}&#160;<b>${commodity.name}</b></div>
    <div class="titsonfire-card-container mdl-grid">
        <!-- commodity card -->
        <!-- ITEM IMAGES -->
        <div class="mdl-cell mdl-cell--12-col mdl-card mdl-shadow--1dp">
            <div class="images" id="images">
                <img id="mainImage" class="item-image" src="${commodity.images[0]}"/>
                <div class="images-navi mdl-grid">
                    <c:forEach items="${commodity.images}" var="image" varStatus="loop">
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
                    <strong>${commodity.type.name}</strong>&#160;<span>${commodity.shortDescription}</span>
                </div>
                    <h3 class="mdl-cell mdl-cell--12-col mdl-typography--headline commodity-name">${commodity.type.name}&#160; ${commodity.name}</h3>
                    <div id="vendor-code" class="mdl-cell mdl-cell--12-col mdl-typography--headline">${labelVendorCode}: ${commodity.id}-${commodity.branches[0].id}</div>
                    <div class="mdl-cell mdl-cell--12-col mdl-typography--headline" >${labelPrice} &#160;<b>${commodity.branches[0].price} ${commodity.branches[0].currency}</b></div>
                    <div class="mdl-cell mdl-cell--6-col mdl-typography--font-light no-padding">
                        <span class="mdl-typography--font-light mdl-typography--subhead">${commodity.overview}</span>
                    </div>
                    <div class="mdl-cell mdl-cell--6-col">
                         <img class="article-image" src="${commodity.lastImageUri}" width="200px" border="0" alt=""/>
                    </div>
                    <div class="mdl-grid mdl-cell--12-col">
                        <div class="mdl-cell mdl-cell--8-col">
                            <h3 class="mdl-typography--headline">Attributes:</h3>
                            <h3 class="mdl-typography--headline">
                            <div id="attribute-container">
                            </div>
                            <div id="amount-container">

                            </div>
                            <div id="vendor-code-container">
                                ${labelVendorCode}: ${commodity.id}-${commodity.branches[0].id}
                            </div>
                        </div>
                        <div class="mdl-cell mdl-cell--4-col">
                            <div id="action-container">
                                <br/><br/>
                                <!-- ACTIONS WITH commodity -->
                                <!-- Accent-colored raised button with ripple -->
                                <button id="btn-add-to-basket" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                                     ${labelBasket}
                                </button>&#160;<br/><br/>
                                <button id="btn-proceed" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                                    ${labelCheckout}
                                </button>
                            </div>
                        </div>
                    </div>

            </div>
        </div>
        <!--end: commodity card-->
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