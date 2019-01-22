package com.newport.app.ui.login;

import android.support.annotation.NonNull;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.LoginResponse;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.util.PreferencesHeper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tohure on 30/11/17.
 */

class LoginInteractor {

    @AddTrace(name = "login")
    static void login(final String dni, String password, final LoginContract.Callback callback) {
        Call<LoginResponse> call = NewPortApiManager.apiManager().login3(dni, password, PreferencesHeper.getKeyDeviceToken(NewPortApplication.getAppContext()));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        callback.getLoginSucces();
                        saveUserData(response.body());
                    } else if (response.body().getMessage().equals("default_password")) {
                        callback.getLoginError("default_password");
                    } else if (response.body().getMessage().equals("forgotten_password")) {
                        callback.getLoginError("forgotten_password");
                    } else if (response.body().getMessage().equals("incorrect_password")) {
                        callback.getLoginError("incorrect_password");
                    } else {
                        callback.getLoginError(NewPortApplication.getAppContext().getString(R.string.login_error));
                    }
                } else if (response.code() == 401) {
                    callback.getLoginError(NewPortApplication.getAppContext().getString(R.string.dni_unauthorized));
                } else {
                    callback.getLoginError(NewPortApplication.getAppContext().getString(R.string.login_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                //callback.getLoginFailure(t.getMessage());
                callback.getLoginFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "regiserUserToApp")
    static void regiserUserToApp(String cod_sap, String mail, final LoginContract.CallbackRegistration callbackRegistration) {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setCod_sap(cod_sap);
        userRegisterRequest.setMail(mail);

        Call<UserRegisterResponse> call = NewPortApiManager.apiManager().userRegistrationToApp(userRegisterRequest);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserRegisterResponse> call, @NonNull Response<UserRegisterResponse> response) {
                if (response.isSuccessful()) {
                    callbackRegistration.userRegistrationSuccess(response.body());
                } else {
                    callbackRegistration.userRegistrationError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserRegisterResponse> call, @NonNull Throwable t) {
                callbackRegistration.userRegistrationFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "changeUserPassword")
    static void changeUserPassword(UserRegisterRequest userRegisterRequest, final LoginContract.CallbackChangePassword callbackChangePassword) {
        Call<UserRegisterResponse> call = NewPortApiManager.apiManager().changeUserPassword(userRegisterRequest);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserRegisterResponse> call, @NonNull Response<UserRegisterResponse> response) {
                if (response.isSuccessful()) {
                    callbackChangePassword.changeUserPasswordSucces(response.body());
                } else {
                    callbackChangePassword.changeUserPasswordError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserRegisterResponse> call, @NonNull Throwable t) {
                callbackChangePassword.changeuserPasswordFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    private static void saveUserData(LoginResponse loginResponse) {
        PreferencesHeper.setDniUser(NewPortApplication.getAppContext(), loginResponse.getDni());
        PreferencesHeper.setSapCodeUser(NewPortApplication.getAppContext(), loginResponse.getSap_code());
    }
}
