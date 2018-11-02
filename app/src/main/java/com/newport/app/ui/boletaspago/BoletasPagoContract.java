package com.newport.app.ui.boletaspago;

import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.util.BasePresenter;

public interface BoletasPagoContract {
    interface View {
        void showBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void showBoletasPagoError(String error);
    }

    interface Presenter extends BasePresenter<View> {
        void getBoletasPago();
    }

    interface Callback {
        void getBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void getBoletasPagoError(String error);
        void getBoletasPagoFailure(String failure);
    }

    interface ViewValidateAccessBoletaPago {
        void showValidateAccessBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void showValidateAccessBoletaPagoError(String error);
    }

    interface PresenterValidateAccessBoletaPago extends BasePresenter<ViewValidateAccessBoletaPago> {
        void validateAccessBoletaPago(String password_user);
    }

    interface CallbackValidateAccessBoletaPago {
        void getValidateAccessBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void getValidateAccessBoletaPagoError(String error);
        void getValidateAccessBoletaPagoFailure(String failure);
    }

    interface ViewVerificationUserAllowBoletaPago {
        void showVerificationUserAllowBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void showVerificationUserAllowBoletaPagoError(String error);
    }

    interface PresenterVerificationUserAllowBoletaPago extends BasePresenter<ViewVerificationUserAllowBoletaPago> {
        void verificationUserAllowBoletaPago();
    }

    interface CallbackVerificationUserAllowBoletaPago {
        void getVerificationUserAllowBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse);
        void getVerificationUserAllowBoletaPagoError(String error);
        void getVerificationUserAllowBoletaPagoFailure(String failure);
    }
}
