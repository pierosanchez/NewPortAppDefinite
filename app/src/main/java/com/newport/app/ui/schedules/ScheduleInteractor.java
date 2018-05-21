package com.newport.app.ui.schedules;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.ScheduleResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tohure on 15/11/17.
 */

class ScheduleInteractor {

    @AddTrace(name = "getSchedules")
    static void getSchedules(final ScheduleContract.Callback callback) {
        Call<List<ScheduleResponse>> call = NewPortApiManager.apiManager().getSchedules();

        call.enqueue(new Callback<List<ScheduleResponse>>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<List<ScheduleResponse>> call, @NonNull Response<List<ScheduleResponse>> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        callback.getSchedulesSucces(response.body());
                    } else {
                        callback.getSchedulesError("No hay horarios");
                    }
                } else {
                    callback.getSchedulesError("Error al hacer petición");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduleResponse>> call, @NonNull Throwable t) {
                //callback.getSchedulesFailure(t.getMessage());
                callback.getSchedulesFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getUserSchedules")
    static void getUserSchedules(String dni, final ScheduleContract.Callback callback) {
        Call<UserScheduleResponse> call = NewPortApiManager.apiManager().getUserSchedule(dni);

        call.enqueue(new Callback<UserScheduleResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<UserScheduleResponse> call, @NonNull Response<UserScheduleResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.getUserSchedulesSuccess(response.body());
                    } else {
                        callback.getUserSchedulesError("Usted no tiene horario programado");
                    }
                } else {
                    callback.getUserSchedulesError("Error al hacer petición");
                }
            }

            @Override
            public void onFailure( @NonNull Call<UserScheduleResponse> call, @NonNull Throwable t) {
                callback.getUserSchedulesFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
