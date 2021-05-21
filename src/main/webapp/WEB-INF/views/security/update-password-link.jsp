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
    function updatePasswordCodeSuccess(json) {
        $("#update-password").hide();
        $("#update-password-success").show();
        showToast("You have successfully changed your password.");
    }

    function updatePasswordCodeFail(json) {
        $("#update-password").hide();
        $("#update-password-fail").show();
        showErrorFromJson(json);
    }

    function updatePasswordAction() {
        if ($("#password").val() !== $("#repeat-password").val()) {
            showToast("The passwords you entered are not identical. Re-enter your new password");
            return;
        }
        const urlService = URL_SERVICES + "/public/customer/update-password";
        const updatePasswordReq = {
            customerEmail: '${email}',
            newPassword: $("#password").val(),
            resetPasswordCode: '${code}'
        };
        console.log("updatePasswordReq > " + updatePasswordReq);
        sendDataAsJson(urlService, "POST", updatePasswordReq, updatePasswordCodeSuccess, updatePasswordCodeFail);
    }

    function initialView() {
        $("#update-password").show();
        $("#update-password-success").hide();
    }

    $(document).ready(function () {

        initialView();

        const btnResetPassword = document.querySelector('#btn-update-password');
        btnResetPassword.addEventListener('click', function () {
            console.log("Change password");
            updatePasswordAction();
        });

        const btnBack = document.querySelector('#btn-back-to-login');
        btnBack.addEventListener('click', function () {
            console.log("Back to Login password");
            window.location.href = "${loginUrl}";
        });

        const btnBackSuccess = document.querySelector('#btn-back-to-login-success');
        btnBackSuccess.addEventListener('click', function () {
            console.log("Back to Login password");
            window.location.href = "${loginUrl}";
        });

    });
</script>
<div class="titsonfire-more-section">
    <div class="titsonfire-section-title">TiTS ON FIRE SECURITY</div>
    <div id="update-password" class="titsonfire-card-container mdl-grid">
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">CHANGE YOU PASSWORD</h2>
            </div>
            <div class="mdl-card__supporting-text">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                    <input class="mdl-textfield__input" type="password" id="password" name='password'>
                    <label class="mdl-textfield__label" for="password">New password</label>
                </div>
                <br/>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>

        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div class="mdl-card__supporting-text">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                    <input class="mdl-textfield__input" type="password" id="repeat-password" name='password'>
                    <label class="mdl-textfield__label" for="password">Repeat New password</label>
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
                    <button id="btn-update-password"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        CHANGE MY PASSWORD
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

    <div id="update-password-success" class="titsonfire-card-container mdl-grid">
        <div class="mdl-cell mdl-cell--12-col" style="height:150px">
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">Password updated successfully</h2>
                <span class="mdl-typography--font-light mdl-typography--subhead">
                    You have successfully changed your password.
                </span>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <!-- Forgot password -->
        <div class="mdl-cell mdl-cell--4">
        </div>
        <div class="mdl-cell mdl-cell--4">
            <button id="btn-back-to-login-success"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                BACK TO LOGIN
            </button>
        </div>
        <div class="mdl-cell mdl-cell--4">
        </div>
        <!-- end: security card -->
    </div>
</div>