package com.newport.app.ui.scheduleprocess;

import android.support.annotation.NonNull;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.UserScheduleResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchSchedule2Iteractor {

    @AddTrace(name = "")
    static void getUserSchedules(final SwitchScheduleContract.CallBack2 callBack2) {
        String currentSapCode = PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext());

        Call<UserScheduleResponse> call = NewPortApiManager.apiManager().getUserSchedules(currentSapCode);

        call.enqueue(new Callback<UserScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserScheduleResponse> call, @NonNull Response<UserScheduleResponse> response) {
                if (response.isSuccessful()) {
                    callBack2.getUserSchedulesSuccess(response.body());
                } else {
                    callBack2.getUserScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserScheduleResponse> call, @NonNull Throwable t) {
                callBack2.getUserScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "")
    static void getUserOff(final SwitchScheduleContract.Callback3 callBack3, String dayOff) {

        Call<List<UserScheduleResponse>> call = NewPortApiManager.apiManager().getUsersOff(dayOff);

        call.enqueue(new Callback<List<UserScheduleResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Response<List<UserScheduleResponse>> response) {
                if (response.isSuccessful()) {
                    callBack3.getUsersOffSuccess(response.body());
                } else {
                    callBack3.getUsersOffError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Throwable t) {
                callBack3.getUsersOffError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "")
    static void getUserWork(final SwitchScheduleContract.CallBack4 callBack4, String dayWork) {

        Call<List<UserScheduleResponse>> call = NewPortApiManager.apiManager().getUserWork(dayWork);

        call.enqueue(new Callback<List<UserScheduleResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Response<List<UserScheduleResponse>> response) {
                if (response.isSuccessful()) {
                    callBack4.getUserWorkSuccess(response.body());
                } else {
                    callBack4.getUserWorkError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Throwable t) {
                callBack4.getUserWorkError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
