package com.newport.app.ui.newdetail;

import com.newport.app.data.models.request.NewsLogRequest;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.data.models.response.NewResponse;
import com.newport.app.util.BasePresenter;

/**
 * Created by tohure on 15/11/17.
 */

public interface NewDetailContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showNew(NewResponse newResponse);

        void showNewError(String error);

    }

    interface Presenter extends BasePresenter<View> {

        void makeNewQuery(int idNew);

    }

    interface Callback {

        void getNewSucces(NewResponse newResponse);

        void getNewError(String error);

        void getNewFailure(String message);
    }

    interface NewsLogView {
        void showSaveNewsLogSuccess(GenericResponse genericResponse);

        void showSaveNewLogError(String error);
    }

    interface NewsLogPresenter extends BasePresenter<NewsLogView> {
        void saveNewsLog(NewsLogRequest newsLogRequest);
    }

    interface NewsLogCallback {
        void getSaveNewsLogSucces(GenericResponse genericResponse);

        void getNewsLogError(String error);

        void getNewsLogFailure(String failure);
    }
}
