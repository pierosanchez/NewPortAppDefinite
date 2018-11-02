package com.newport.app.ui.boletaspago;

import android.support.annotation.NonNull;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.util.PreferencesHeper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class BoletasPagoInteractor {
    static void getDirectory(final BoletasPagoContract.Callback callback) {
        Call<BoletasPagoResponse> call = NewPortApiManager.apiManager().getBoletasPago(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()));

        call.enqueue(new Callback<BoletasPagoResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<BoletasPagoResponse> call, @NonNull Response<BoletasPagoResponse> response) {

                if (response.isSuccessful()) {
                    callback.getBoletasPagoSuccess(response.body());
                } else {
                    callback.getBoletasPagoError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoletasPagoResponse> call, @NonNull Throwable t) {
                callback.getBoletasPagoFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void validateAccessBoletaPago(String password_user, final BoletasPagoContract.CallbackValidateAccessBoletaPago callbackValidateAccessBoletaPago) {
        Call<BoletasPagoResponse> call = NewPortApiManager.apiManager().validateAccessBoletasPago(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()), password_user);

        call.enqueue(new Callback<BoletasPagoResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<BoletasPagoResponse> call, @NonNull Response<BoletasPagoResponse> response) {

                if (response.isSuccessful()) {
                    callbackValidateAccessBoletaPago.getValidateAccessBoletaPagoSuccess(response.body());
                } else {
                    callbackValidateAccessBoletaPago.getValidateAccessBoletaPagoError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoletasPagoResponse> call, @NonNull Throwable t) {
                callbackValidateAccessBoletaPago.getValidateAccessBoletaPagoFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void verificationUserAllowBoletaPago(final BoletasPagoContract.CallbackVerificationUserAllowBoletaPago callbackVerificationUserAllowBoletaPago) {
        Call<BoletasPagoResponse> call = NewPortApiManager.apiManager().verificationUserAllowBoletaPagoPresenter(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()));

        call.enqueue(new Callback<BoletasPagoResponse>() {
            @Override
            public void onResponse(@NonNull Call<BoletasPagoResponse> call,@NonNull Response<BoletasPagoResponse> response) {
                if (response.isSuccessful()) {
                    callbackVerificationUserAllowBoletaPago.getVerificationUserAllowBoletaPagoSuccess(response.body());
                } else {
                    callbackVerificationUserAllowBoletaPago.getVerificationUserAllowBoletaPagoError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoletasPagoResponse> call,@NonNull Throwable t) {
                callbackVerificationUserAllowBoletaPago.getVerificationUserAllowBoletaPagoFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
