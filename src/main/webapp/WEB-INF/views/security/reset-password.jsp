<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:theme code="webRoot" var="webRoot"/>
<spring:url value="${webRoot}/" var="app_url"/>
<spring:url value="${webRoot}/security/in/" var="loginUrl"/>
<spring:url value="${webRoot}/customer/account/reset-password" var="resetPasswordUrl"/>
<script type="text/javascript">
    function resetPasswordCodeSuccess(json) {
        $("#reset-password").hide();
        $("#reset-password-success").show();
        showToast("Please check your e-mail. Reset password link sent.");
    }

    function resetPasswordCodeFail(json) {
        $("#reset-password").hide();
        $("#reset-password-fail").show();
        showErrorFromJson(json);
    }

    function resetPasswordAction() {
        let accountEmail = $("#email").val();
        const urlService = URL_SERVICES + "/public/customer/reset-password-code";
        const resetPasswordReq = {
            customerEmail: accountEmail
        };
        console.log("resetPasswordReq > " + resetPasswordReq);
        sendDataAsJson(urlService, "POST", resetPasswordReq, resetPasswordCodeSuccess, resetPasswordCodeFail);
        console.log("email to reset password " + accountEmail);
    }

    function initialView() {
        $("#reset-password").show();
        $("#reset-password-success").hide();
    }

    $(document).ready(function () {

        initialView();

        const btnResetPassword = document.querySelector('#btn-reset-password');
        btnResetPassword.addEventListener('click', function () {
            console.log("Reset password");
            resetPasswordAction();
        });

        const btnBack = document.querySelector('#btn-back-to-login');
        btnBack.addEventListener('click', function () {
            console.log("Back to Login password");
            window.location.href = "${loginUrl}";
        });

    });
</script>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title">TiTS ON FIRE SECURITY</div>
    <div id="reset-password" class="titsonfire-card-container mdl-grid">
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4" style="width:150px;">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">RESET PASSWORD</h2>
            </div>
            <div class="mdl-card__supporting-text">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                    <input class="mdl-textfield__input" type="text" id="email" name='email'>
                    <label class="mdl-textfield__label" for="email">e-mail</label>
                </div>
                <br/>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--12-col">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--4">
                </div>
                <div class="mdl-cell mdl-cell--4">
                    <button id="btn-reset-password"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        RESET PASSWORD
                    </button>
                </div>
                <div class="mdl-cell mdl-cell--4">
                </div>
                <!-- Forgot password -->
                <div class="mdl-cell mdl-cell--4">
                </div>
                <div class="mdl-cell mdl-cell--4">
                    <button id="btn-back-to-login"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        BACK TO LOGIN
                    </button>
                </div>
                <div class="mdl-cell mdl-cell--4">
                </div>
                <!--end: sForgot password-->
            </div>
        </div>
        <!-- end: security card -->
    </div>

    <div id="reset-password-success" class="titsonfire-card-container mdl-grid">
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4" style="width:450px;">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">RESET PASSWORD LINK SENT</h2>
            </div>
            <div class="mdl-card__supporting-text">
             <span class="mdl-typography--font-light mdl-typography--subhead">
                 If you are registered with TitsOnFire.store an email will be on its way.&nbsp;
                 If you do not receive the email within 15 minutes, contact us info@titsonfire.store
            </span>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <!-- end: security card -->
    </div>
</div>