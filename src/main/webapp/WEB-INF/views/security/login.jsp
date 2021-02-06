<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/" var="app_url"/>
<spring:url value="${webRoot}/customer/account/create/checkout" var="createAccountUrl"/>

<!-- Square card -->
<style>
.demo-card-square.mdl-card {
  width: 320px;
  height: 320px;
}
.demo-card-square > .mdl-card__title {
  color: #fff;
  background: #ea38ff;
}
</style>
<script type="text/javascript">
$(document).ready(function () {

  var btnCreateAcc = document.querySelector('#btn-create-account');
  btnCreateAcc.addEventListener('click', function() {
    console.log("${createAccountUrl}");
    window.location.href = "${createAccountUrl}";
  } );

});
</script>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title mdl-typography--display-1-color-contrast">TiTS ON FIRE SECURITY</div>
    <div class="titsonfire-card-container mdl-grid">
        <!-- static card -->
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--12-col mdl-card">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12 login-form demo-card-square mdl-card mdl-shadow--2dp " >
                    <form name='login' action='${app_url}login' method='POST'>
                        <div class="mdl-card__title">
                            <h2 class="mdl-card__title-text">Welcome</h2>
                        </div>
                        <div class="mdl-card__supporting-text">
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <input class="mdl-textfield__input" type="text" id="username" name='username'>
                                <label class="mdl-textfield__label" for="username">username</label>
                            </div>
                            <br/>
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <input class="mdl-textfield__input" type="password" id="password"
                                       name='password'>
                                <label class="mdl-textfield__label" for="password">password</label>
                            </div>
                        </div>
                        <div class="mdl-card__actions mdl-card--border">
                            <button class="mdl-cell mdl-cell--12 mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                                Login
                            </button>
                        </div>
                    </form>
                </div>
                <div class="mdl-cell mdl-cell--12">
                    <button id="btn-create-account" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        CREATE ACCOUNT
                    </button>
                </div>
            </div>
        </div>
        <!--end: static card-->
    </div>
</div>