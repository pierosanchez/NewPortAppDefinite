package com.newport.app.ui.boletaspago;

import com.newport.app.data.models.response.BoletasPagoResponse;

public class ValidateAccessBoletaPagoPresenter implements BoletasPagoContract.PresenterValidateAccessBoletaPago, BoletasPagoContract.CallbackValidateAccessBoletaPago {

    BoletasPagoContract.ViewValidateAccessBoletaPago viewValidateAccessBoletaPago;

    @Override
    public void validateAccessBoletaPago(String password_user) {
        BoletasPagoInteractor.validateAccessBoletaPago(password_user, this);
    }

    @Override
    public void getValidateAccessBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        viewValidateAccessBoletaPago.showValidateAccessBoletaPagoSuccess(boletasPagoResponse);
    }

    @Override
    public void getValidateAccessBoletaPagoError(String error) {
        viewValidateAccessBoletaPago.showValidateAccessBoletaPagoError(error);
    }

    @Override
    public void getValidateAccessBoletaPagoFailure(String failure) {
        viewValidateAccessBoletaPago.showValidateAccessBoletaPagoError(failure);
    }

    @Override
    public void attachedView(BoletasPagoContract.ViewValidateAccessBoletaPago view) {
        this.viewValidateAccessBoletaPago = view;
    }

    @Override
    public void detachView() {
        this.viewValidateAccessBoletaPago = null;
    }
}
