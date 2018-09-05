package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;

public class SwitchSchedule4Presenter implements SwitchScheduleContract.Presenter4, SwitchScheduleContract.CallBack4 {

    SwitchScheduleContract.View4 view4;

    @Override
    public void getUsersWork(String dayWork) {
        SwitchSchedule2Iteractor.getUserWork(this, dayWork);
    }

    @Override
    public void getUserWorkSuccess(List<UserScheduleResponse> userScheduleResponseList) {
        view4.showUserWorkSuccess(userScheduleResponseList);
    }

    @Override
    public void getUserWorkError(String error) {
        view4.showUsersWorkError(error);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View4 view) {
        view4 = view;
    }

    @Override
    public void detachView() {

    }
}
