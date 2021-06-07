<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/shopping/cart/" var="backUrl"/>
<spring:url value="${webRoot}/customer/account/update/" var="profileUrl"/>
<spring:url value="${webRoot}/commodity" var="showCommodityUrl"/>

<script type="text/javascript">
  $(document).ready(function () {
  });
</script>
<div class="titsonfire-more-section">
  <div class="titsonfire-section-title">Thank you!</div>
  <div class="titsonfire-card-container mdl-grid">
    <!-- checkout card -->
    <div class="mdl-cell mdl-cell--12-col mdl-card mdl-shadow--2dp">
      <div class="mdl-grid titsonfire-card-content">
        <div id="payment-confirmed" class="mdl-cell mdl-cell--6-col">
          <div class="mdl-grid">
            <div class="mdl-cell mdl-cell--2-col">&nbsp;</div>
            <div class="mdl-cell mdl-cell--8-col">
              Thank you for your purchase!<br/>
              Order #<b id="order-id">${orderId}</b> confirmed<br/>
              You can track the order status in your <a href="${profileUrl}">profile</a>:
              You will receive order status updates by email.<br/>
              Estimated time of dispatch: 1-3 days
            </div>
            <div class="mdl-cell mdl-cell--2-col">&nbsp;</div>
          </div>
        </div>

        <div id="delivery-info" class="mdl-cell mdl-cell--6-col">
          <b>Delivery Address:</b><br/>
          ${customer.email}<br/>
          ${customer.fullName}<br/>
          ${customer.country}, ${customer.postcode}, ${customer.city}, ${customer.address}
        </div>

      </div>
    </div>
    <!--end: commodity card-->
  </div>
</div>
