package com.newport.app.ui.boletaspago;

import com.newport.app.data.models.response.BoletasPagoResponse;

public class BoletasPagoPresenter implements BoletasPagoContract.Presenter, BoletasPagoContract.Callback {

    private BoletasPagoContract.View view;

    @Override
    public void getBoletasPago() {
        BoletasPagoInteractor.getDirectory(this);
    }

    @Override
    public void getBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        view.showBoletasPagoSuccess(boletasPagoResponse);
    }

    @Override
    public void getBoletasPagoError(String error) {
        view.showBoletasPagoError(error);
    }

    @Override
    public void getBoletasPagoFailure(String failure) {
        view.showBoletasPagoError(failure);
    }

    @Override
    public void attachedView(BoletasPagoContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
