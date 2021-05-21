<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/" var="app_url"/>
<spring:url value="${webRoot}/customer/account/create/checkout" var="createAccountUrl"/>
<spring:url value="${webRoot}/customer/account/reset-password" var="resetPasswordUrl"/>
<script type="text/javascript">
    $(document).ready(function () {
        const queryString = window.location.search;
        console.log('queryString = ' + queryString);
        if (queryString.includes('error')) {
            $("#security-error").show();
        } else {
            $("#security-error").hide();
        }
        var btnCreateAcc = document.querySelector('#btn-create-account');
        btnCreateAcc.addEventListener('click', function () {
            console.log("${createAccountUrl}");
            window.location.href = "${createAccountUrl}";
        });
        var btnResetPassword = document.querySelector('#btn-reset-password');
        btnResetPassword.addEventListener('click', function () {
            console.log("${resetPasswordUrl}");
            window.location.href = "${resetPasswordUrl}";
        });
    });
</script>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title">TiTS ON FIRE SECURITY</div>
    <div class="titsonfire-card-container mdl-grid">
        <!-- static card -->
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div id="security-error" class="mdl-cell mdl-cell--12-col">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--4">
                </div>
                <div class="mdl-cell mdl-cell--4">
                    <!-- Contact Chip -->
                    <span class="mdl-chip mdl-chip--contact">
                        <span class="mdl-chip__contact mdl-color--teal mdl-color-text--white"><i class="material-icons">warning_amber</i></span>
                        <span class="mdl-chip__text">Wrong login information</span>
                    </span>
                </div>
                <div class="mdl-cell mdl-cell--4">
                </div>
            </div>
        </div>
        <!-- security card -->
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <form name='login' action='${app_url}login' method='POST' class="mdl-card">
                <div class="mdl-card__title">
                    <h2 class="mdl-card__title-text ">Welcome</h2>
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
                <div class="mdl-card__actions">
                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        Login
                    </button>
                </div>
            </form>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <!-- end: security card -->

        <!-- CREATE LOGIN -->
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div style="padding:3px">
                <button id="btn-create-account"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                    CREATE ACCOUNT
                </button>
            </div>
            <div style="padding:3px">
                <button id="btn-reset-password"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                    FORGOT PASSWORD
                </button>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>


    </div>
</div>