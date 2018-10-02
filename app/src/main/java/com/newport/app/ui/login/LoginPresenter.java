package com.newport.app.ui.login;

import com.newport.app.data.models.response.UserRegisterResponse;

/**
 * Created by tohure on 29/11/17.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Callback {

    private LoginContract.View loginView;

    @Override
    public void login(String dni, String password) {
        loginView.showLoading();
        LoginInteractor.login(dni, password, this);
    }

    @Override
    public void attachedView(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void detachView() {
        loginView = null;
    }

    @Override
    public void getLoginSucces() {
        loginView.loginSucces();
        loginView.hideLoading();
    }

    @Override
    public void getLoginError(String error) {
        loginView.loginError(error);
        loginView.hideLoading();
    }

    @Override
    public void getLoginFailure(String message) {
        loginView.loginError(message);
        loginView.hideLoading();
    }
}
