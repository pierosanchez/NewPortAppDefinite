package com.newport.app.ui.login;

import com.newport.app.data.models.response.UserRegisterResponse;

public class RegisterPresenter implements LoginContract.PresenterUserRegistration, LoginContract.CallbackRegistration {

    private LoginContract.ViewUserRegistration viewUserRegistration;

    @Override
    public void userRegistration(String cod_sap, String mail) {
        viewUserRegistration.showLoading();
        LoginInteractor.regiserUserToApp(cod_sap, mail, this);
    }

    @Override
    public void userRegistrationSuccess(UserRegisterResponse userRegisterResponse) {
        viewUserRegistration.userRegistrationSuccess(userRegisterResponse);
        viewUserRegistration.hideLoading();
    }

    @Override
    public void userRegistrationError(String error) {
        viewUserRegistration.userRegistrationError(error);
        viewUserRegistration.hideLoading();
    }

    @Override
    public void userRegistrationFailure(String failure) {
        viewUserRegistration.userRegistrationError(failure);
        viewUserRegistration.hideLoading();
    }

    @Override
    public void attachedView(LoginContract.ViewUserRegistration view) {
        this.viewUserRegistration = view;
    }

    @Override
    public void detachView() {
        viewUserRegistration = null;
    }
}
