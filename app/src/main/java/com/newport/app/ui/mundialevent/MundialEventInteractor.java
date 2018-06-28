package com.newport.app.ui.mundialevent;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.MatchsResponse;
import com.newport.app.data.models.response.UserElectionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MundialEventInteractor {
    @AddTrace(name = "getMetches")
    static void getMatches(final MundialEventContract.Callback callback) {

        Call<List<MatchsResponse>> call = NewPortApiManager.apiManager().getMatchs();

        call.enqueue(new Callback<List<MatchsResponse>>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<List<MatchsResponse>> call, @NonNull Response<List<MatchsResponse>> response) {

                if (response.isSuccessful()) {
                    callback.getMundialEventSucces(response.body());
                } else {
                    callback.getMundialEventError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<MatchsResponse>> call, @NonNull Throwable t) {
                callback.getMundialEventFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "setUserElection")
    static void setUserElection(String dni, String election1, String election2, String election3, String election4, String id_partido1, String id_partido2,
                                String id_partido3, String id_partido4, final MundialEventContract.Callback callback) {

        Call<UserElectionResponse> call = NewPortApiManager.apiManager().setUserElection(dni, election1, election2, election3, election4, id_partido1, id_partido2, id_partido3, id_partido4);
        Log.d("setUserElection", "entro!!" + dni);

        call.enqueue(new Callback<UserElectionResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<UserElectionResponse> call, @NonNull Response<UserElectionResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("onResponse", "entro!!");
                    callback.setUserElectionSuccess(response.body());
                } else {
                    Log.d("onResponseElse", "entro!!");
                    callback.setUserElectionError(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserElectionResponse> call, @NonNull Throwable t) {
                callback.setUserElectionFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "validateUserElection")
    static void validateUserElection(String dni, String election1, final MundialEventContract.Callback callback) {

        Call<UserElectionResponse> call = NewPortApiManager.apiManager().validateUserElection(dni, election1);
        Log.d("setUserElection", "entro!!" + dni);

        call.enqueue(new Callback<UserElectionResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<UserElectionResponse> call, @NonNull Response<UserElectionResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("onResponse", "entro!!");
                    callback.validateUserElectionSuccess(response.body());
                } else {
                    Log.d("onResponseElse", "entro!!");
                    callback.validateUserElectionError(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserElectionResponse> call, @NonNull Throwable t) {
                Log.d("onFailure", t.getMessage());
                callback.validateUserElectionFaulure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
