package com.newport.app.ui.newdetail;

import com.newport.app.data.models.request.NewsLogRequest;
import com.newport.app.data.models.response.GenericResponse;

public class NewsLogPresenter implements NewDetailContract.NewsLogPresenter, NewDetailContract.NewsLogCallback {

    private NewDetailContract.NewsLogView view;

    @Override
    public void saveNewsLog(NewsLogRequest newsLogRequest) {
        NewDetailInteractor.saveNewsLog(newsLogRequest, this);
    }

    @Override
    public void getSaveNewsLogSucces(GenericResponse genericResponse) {
        view.showSaveNewsLogSuccess(genericResponse);
    }

    @Override
    public void getNewsLogError(String error) {
        view.showSaveNewLogError(error);
    }

    @Override
    public void getNewsLogFailure(String failure) {
        view.showSaveNewLogError(failure);
    }

    @Override
    public void attachedView(NewDetailContract.NewsLogView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
