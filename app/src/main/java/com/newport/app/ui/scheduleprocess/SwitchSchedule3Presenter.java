package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;

public class SwitchSchedule3Presenter implements SwitchScheduleContract.Presenter3, SwitchScheduleContract.Callback3 {

    SwitchScheduleContract.View3 view3;

    @Override
    public void getUsersOff(String dayOff) {
        SwitchSchedule2Iteractor.getUserOff(this, dayOff);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View3 view) {
        view3 = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getUsersOffSuccess(List<UserScheduleResponse> userScheduleResponseList) {
        view3.showUsersOffSuccess(userScheduleResponseList);
    }

    @Override
    public void getUsersOffError(String error) {
        view3.ShowUsersOffError(error);
    }
}
