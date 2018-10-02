package com.newport.app.ui.login;

import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.UserRegisterResponse;

public class ChangePasswordPresenter implements LoginContract.PresenterChangePassword, LoginContract.CallbackChangePassword {

    private LoginContract.ViewChangePassword viewChangePassword;

    @Override
    public void changeUserPassword(UserRegisterRequest userRegisterRequest) {
        viewChangePassword.showLoading();
        LoginInteractor.changeUserPassword(userRegisterRequest, this);
    }

    @Override
    public void changeUserPasswordSucces(UserRegisterResponse userRegisterResponse) {
        viewChangePassword.changeUserPasswordSuccess(userRegisterResponse);
        viewChangePassword.hideLoading();
    }

    @Override
    public void changeUserPasswordError(String error) {
        viewChangePassword.changeUserPasswordError(error);
        viewChangePassword.hideLoading();
    }

    @Override
    public void changeuserPasswordFailure(String failure) {
        viewChangePassword.changeUserPasswordError(failure);
        viewChangePassword.hideLoading();
    }

    @Override
    public void attachedView(LoginContract.ViewChangePassword viewChangePassword) {
        this.viewChangePassword = viewChangePassword;
    }

    @Override
    public void detachView() {
        viewChangePassword = null;
    }
}
