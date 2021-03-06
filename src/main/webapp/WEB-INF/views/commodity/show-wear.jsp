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
var purchaseInfo = {
    branchId: null,
    amount: 1,
    price: null,
    commodityName: "${commodity.name}",
    commodityImageUri: "${commodity.images[0]}"
};
var BRANCHES = [];
var content = "";
var SELECTED_SIZE;
var SELECTED_COLOR;
var SELECTED_BRANCH;
var AMOUNT;
var PRICE;
var shoppingCartObj;

function addToShoppingCart() {
    purchaseInfo.branchId = SELECTED_BRANCH;
    purchaseInfo.price = PRICE;
    addToShoppingCartSet(shoppingCartId, purchaseInfo, addToShoppingCartSuccess, addToShoppingCartFail);
}

function showVendorCode(){
    var content = '${labelVendorCode}: ' + commodityId +'-'+SELECTED_BRANCH;
    $('#vendor-code-container').empty();
    $('#vendor-code-container').append(content);
    $('#vendor-code-container').show();

    $('#vendor-code').empty();
    $('#vendor-code').append(content);
    $('#vendor-code').show();
}

function findBranch() {
    BRANCHES.forEach( function(value) {
        if( value.sizes[0].value===SELECTED_SIZE && value.colors[0].value==SELECTED_COLOR ){
            SELECTED_BRANCH = value.id;
            AMOUNT = value.amount;
            PRICE = value.price;
        }
    } );
    content = "${labelAmount}:&#160;" + AMOUNT;
    $('#amount-container').empty();
    $('#amount-container').append(content);
    $('#amount-container').show();

    //show vendor code
    showVendorCode();

    //showToast( "SELECTED BRANCH : " + SELECTED_BRANCH );
};

function onColorClick(el){

    var elms = document.getElementsByClassName("colorCircleSelect");
    //
    for(var i = 0; i < elms.length; i++){
       elms[i].className = "colorCircleSelect";
    }
    el.className = "colorCircleSelect circleSelection";
    var color = el.getAttribute("value");
    SELECTED_COLOR = color;

    //DRAW SIZES
    $('#action-container').show();
    findBranch();
    //showToast("SELECTED SIZE & COLOR : " +SELECTED_SIZE + " | " + SELECTED_COLOR);
    componentHandler.upgradeDom();
}

function genColorContent(value, index){
    content += '<div id="color-'+value+'" class="colorCircleSelect" style="background:'+value+';" onclick="onColorClick(this);" value="'+value+'">&#160;&#160;&#160;&#160;&#160;</div>&#160;';
};

function showColors(el){
    var size = el.getAttribute("value");
    SELECTED_SIZE = size;
    //showToast("showColors " + SELECTED_SIZE);
    var colors = [];
    BRANCHES.forEach( function(value){ if( value.sizes[0].value===size ){colors.push( value.colors[0].value );} } );
    colors.sort();
    colors.reverse();
    content = "${labelColor}:&#160;";
    colors.forEach(genColorContent);

    $('#color-container').empty();
    $('#color-container').append(content);
    $('#color-container').show();
    componentHandler.upgradeDom();

    //find element auto select first color
    var element = document.getElementById('color-'+colors[0]);
    onColorClick(element);

};

function genSizeContent(value, index){
   content += '<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="size-'+index+'">';
   content += '<input type="radio" id="size-'+index+'" class="mdl-radio__button" name="size" value="'+value+'" onclick="showColors(this);"/>';
   content += '<span class="mdl-radio__label commodity-name">'+value+'</span>';
   content += '</label>&#160;';

};

function showSizes(){
    $('#action-container').hide();
    $('#color-container').hide();
    $('#amount-container').hide();

    var uniqSizes = [];
    BRANCHES.forEach( function(value){ if(!uniqSizes.includes(value.sizes[0].value)){uniqSizes.push(value.sizes[0].value)} });
    uniqSizes.sort();
    uniqSizes.reverse();
    content = "${labelSize}:&#160;";
    uniqSizes.forEach(genSizeContent);
    $('#size-container').empty();
    $('#size-container').append(content);
};


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

function ifAmountIsOneAutoSelectFirstSize() {
    if(AMOUNT==1) {
        var elementRadioBtn = document.getElementById('size-0');
        elementRadioBtn.checked = true;
        showColors(elementRadioBtn);
    }
}

$(document).ready(function () {
getShoppingCart(shoppingCartId, loadCartSuccess);
<c:if test="${not empty commodity}">
var str_branches = '${commodity.branches}';
var objJson = JSON.parse(str_branches);
AMOUNT = 0;
for(var i=0; i<objJson.length; i++){
    var br = objJson[i];
    var sizes = [];
    var colors = [];
    br.attributes.forEach( function(attribute){
        if(attribute.name=="size"){sizes.push({ id: attribute.name, value: attribute.value });}
        if(attribute.name=="color"){colors.push({ id: attribute.name, value: attribute.value });}
    });
    var branch = {
        id : br.id,
        amount : br.amount,
        price: br.price,
        sizes : sizes,
        colors :  colors
      };
    BRANCHES.push(branch);
    AMOUNT += br.amount;
}
</c:if>

var btnPropertyBack = document.querySelector('#btn-add-to-basket');
btnPropertyBack.addEventListener('click', addToShoppingCart);
showSizes();
ifAmountIsOneAutoSelectFirstSize();

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
                    <div id="vendor-code" class="mdl-cell mdl-cell--12-col mdl-typography--headline"></div>
                    <div class="mdl-cell mdl-cell--12-col mdl-typography--headline" >${labelPrice} &#160;<b>${commodity.branches[0].price} ${commodity.branches[0].currency}</b></div>
                    <div class="mdl-cell mdl-cell--6-col mdl-typography--font-light no-padding">
                        <span class="mdl-typography--font-light mdl-typography--subhead">${commodity.overview}</span>
                    </div>
                    <div class="mdl-cell mdl-cell--6-col">
                         <img class="article-image" src="${commodity.lastImageUri}" border="0" alt=""/>
                    </div>
                    <div class="mdl-grid mdl-cell--12-col">
                        <div class="mdl-cell mdl-cell--8-col">

                            <h3 class="mdl-typography--headline">${labelChooseProps} ${labelCheckout}</h3>
                            <h3 class="mdl-typography--headline">
                                <div id="size-container">
                                </div>
                                <div id="color-container">
                                </div>
                            </h3>
                            <div id="amount-container">
                            </div>
                            <div id="vendor-code-container">
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