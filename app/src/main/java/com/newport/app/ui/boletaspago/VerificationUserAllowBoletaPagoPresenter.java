package com.newport.app.ui.boletaspago;

import com.newport.app.data.models.response.BoletasPagoResponse;

public class VerificationUserAllowBoletaPagoPresenter implements BoletasPagoContract.PresenterVerificationUserAllowBoletaPago, BoletasPagoContract.CallbackVerificationUserAllowBoletaPago {

    BoletasPagoContract.ViewVerificationUserAllowBoletaPago viewVerificationUserAllowBoletaPago;

    @Override
    public void verificationUserAllowBoletaPago() {
        BoletasPagoInteractor.verificationUserAllowBoletaPago(this);
    }

    @Override
    public void getVerificationUserAllowBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        viewVerificationUserAllowBoletaPago.showVerificationUserAllowBoletaPagoSuccess(boletasPagoResponse);
    }

    @Override
    public void getVerificationUserAllowBoletaPagoError(String error) {
        viewVerificationUserAllowBoletaPago.showVerificationUserAllowBoletaPagoError(error);
    }

    @Override
    public void getVerificationUserAllowBoletaPagoFailure(String failure) {
        viewVerificationUserAllowBoletaPago.showVerificationUserAllowBoletaPagoError(failure);
    }

    @Override
    public void attachedView(BoletasPagoContract.ViewVerificationUserAllowBoletaPago view) {
        this.viewVerificationUserAllowBoletaPago = view;
    }

    @Override
    public void detachView() {
        this.viewVerificationUserAllowBoletaPago = null;
    }
}
