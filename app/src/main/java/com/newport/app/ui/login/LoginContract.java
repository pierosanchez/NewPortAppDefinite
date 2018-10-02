package com.newport.app.ui.login;

import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.util.BasePresenter;

/**
 * Created by tohure on 29/11/17.
 */

public interface LoginContract {

    interface View {

        void showLoading();

        void hideLoading();

        void loginSucces();

        void loginError(String error);

    }

    interface Presenter extends BasePresenter<View> {

        void login(String dni, String password);

    }

    interface Callback {

        void getLoginSucces();

        void getLoginError(String error);

        void getLoginFailure(String message);
    }

    //registro de los nuevos usuarios
    interface ViewUserRegistration {
        void showLoading();

        void hideLoading();

        void userRegistrationSuccess(UserRegisterResponse userRegisterResponse);

        void userRegistrationError(String error);
    }

    interface PresenterUserRegistration extends BasePresenter<ViewUserRegistration> {
        void userRegistration(String cod_sap, String mail);
    }

    interface CallbackRegistration {
        void userRegistrationSuccess(UserRegisterResponse userRegisterResponse);

        void userRegistrationError(String error);

        void userRegistrationFailure(String failure);
    }

    //cambios de password ya sea por olvido o por tener passwords por defecto
    interface ViewChangePassword {
        void showLoading();

        void hideLoading();

        void changeUserPasswordSuccess(UserRegisterResponse userRegisterResponse);

        void changeUserPasswordError(String error);
    }

    interface PresenterChangePassword extends BasePresenter<ViewChangePassword> {
        void changeUserPassword(UserRegisterRequest userRegisterRequest);
    }

    interface CallbackChangePassword {
        void changeUserPasswordSucces(UserRegisterResponse userRegisterResponse);

        void changeUserPasswordError(String error);

        void changeuserPasswordFailure(String failure);
    }
}
