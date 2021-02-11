<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:eval expression="@environment.getProperty('order.expiredMinutes')" var="labelExpiredMinutes"/>
<spring:message code="label_buy" var="labelBuy"/>
<spring:message code="label_price" var="labelPrice"/>
<spring:message code="label_checkout" var="labelCheckout"/>
<spring:message code="label_add_to_basket" var="labelBasket"/>
<spring:message code="label_choose_props" var="labelChooseProps"/>
<spring:message code="label_color" var="labelColor"/>
<spring:message code="label_size" var="labelSize"/>
<spring:message code="label_amount" var="labelAmount"/>
<spring:message code="label_shoppingCartWelcome" var="labelWelcome"/>
<spring:message code="label_choosePM" var="labelChoosePM"/>
<spring:message code="label_checkout_proceed" var="labelProceedCheckout"/>
<spring:theme code="webRoot" var="webRoot" />
<spring:url value="${webRoot}/commodity" var="commodityUrl"/>
<spring:url value="${webRoot}/shopping/cart/checkout/" var="proceedToCheckoutUrl"/>
<spring:url value="${webRoot}/customer/account/create/cart/" var="createAccountUrl"/>

<script type="text/javascript">
const shoppingCartId = ${ShoppingCartCookie};
const showCommodityUrl = '${commodityUrl}';

var shoppingCartObj;
var currentBranchId; //current branchId for update
var shoppingCartUpdate;
var fromAmountName;

function showCartElements() {
    $("#cart-empty").hide();
    $("#cart-title").show();
    $("#cart-card").show();
    $("#cart-btn-proceed").show();
}

function hideShoppingCart(){
    $("#cart-empty").show();
    $("#cart-title").hide();
    $("#cart-card").hide();
    $("#cart-btn-proceed").hide();
}
function loadCartSuccess(json) {
    shoppingCartObj = json;
    if(shoppingCartObj.shoppingSet.length==0){
        hideShoppingCart();
      }else{
        showShoppingCart(shoppingCartObj);
        showCartElements();
      }
}

$(document).ready(function () {
  $("#cart-empty").hide();
  activateTab('tab-shopping-cart');
  getShoppingCart(shoppingCartId, loadCartSuccess);
  showToast("Welcome to shopping cart!");

  var btnProceed = document.querySelector('#cart-btn-proceed');
  btnProceed.addEventListener('click', function() {
    window.location.href = "${proceedToCheckoutUrl}";
  } );

});
</script>

<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">SHOPPiNG CART</div>
    <div class="titsonfire-card-container mdl-grid">
        <!-- shopping cart -->
        <div id="cart-empty" class="mdl-cell mdl-cell--12-col mdl-card mdl-shadow--1dp" style="padding:10px;">
           <img src="https://mir-s3-cdn-cf.behance.net/project_modules/disp/72b80c113022029.601fdc86e7615.png" alt="Shopping cart is Empty"/>
        </div>
        <div class="mdl-cell mdl-cell--12-col" id="cart-title">
        <h4>Items will be reserved for ${labelExpiredMinutes} minutes</h4>
        Shopping Cart Subtotal (<div class="data-holder" id="total-items">5</div> items):&nbsp;<div class="data-holder" id="total-cart-price">£</div>
        </div>

        <tiles:insertAttribute name="cart-container"/>
        <div class="mdl-cell mdl-cell--6-col mdl-cell--6-col-phone">&nbsp;</div>
        <button id="cart-btn-proceed" class="mdl-cell mdl-cell--4-col mdl-cell--4-col-phone mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
        ${labelProceedCheckout}
        </button>
        <!--end: shopping cart -->
    </div>
</div>
