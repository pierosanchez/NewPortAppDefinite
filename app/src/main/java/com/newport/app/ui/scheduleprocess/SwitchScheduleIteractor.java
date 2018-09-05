package com.newport.app.ui.scheduleprocess;

import android.support.annotation.NonNull;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.util.PreferencesHeper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchScheduleIteractor {

    @AddTrace(name = "getScheduleData")
    static void getScheduleData(final SwitchScheduleContract.Callback callback) {
        String currentDni = PreferencesHeper.getDniUser(NewPortApplication.getAppContext());

        Call<UserResponse> call = NewPortApiManager.apiManager().getScheduleData(currentDni);

        call.enqueue(new Callback<UserResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    callback.getScheduleDataSuccess(response.body());
                } else {
                    callback.getScheduleDataError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                //callback.getUserInfoFailure(t.getMessage());
                callback.getScheduleDataError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
