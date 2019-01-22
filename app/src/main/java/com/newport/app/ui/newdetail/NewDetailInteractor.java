package com.newport.app.ui.newdetail;

import android.support.annotation.NonNull;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.request.NewsLogRequest;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.data.models.response.NewResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tohure on 17/11/17.
 */

class NewDetailInteractor {

    @AddTrace(name = "getNew")
    static void getNew(int idNew, final NewDetailContract.Callback callback) {

        Call<NewResponse> call = NewPortApiManager.apiManager().getNewDetail(idNew);

        call.enqueue(new Callback<NewResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<NewResponse> call, @NonNull Response<NewResponse> response) {

                if (response.isSuccessful()) {
                    callback.getNewSucces(response.body());
                } else {
                    callback.getNewError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewResponse> call, @NonNull Throwable t) {
                //callback.getNewFailure(t.getMessage());
                callback.getNewFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getNew")
    static void saveNewsLog(NewsLogRequest newsLogRequest, final NewDetailContract.NewsLogCallback callback) {

        Call<GenericResponse> call = NewPortApiManager.apiManager().saveNewsLog(newsLogRequest);

        call.enqueue(new Callback<GenericResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    callback.getSaveNewsLogSucces(response.body());
                } else {
                    callback.getNewsLogError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                //callback.getNewFailure(t.getMessage());
                callback.getNewsLogFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
