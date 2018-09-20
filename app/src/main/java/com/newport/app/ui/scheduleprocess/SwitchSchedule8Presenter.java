package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.UserScheduleResponse;

public class SwitchSchedule8Presenter implements SwitchScheduleContract.Presenter8, SwitchScheduleContract.CallBack8  {

    SwitchScheduleContract.View8 view8;

    @Override
    public void getUserScheduleByName(String user_name) {
        SwitchSchedule2Iteractor.getUserScheduleByName(this, user_name);
    }

    @Override
    public void getUserScheduleByNameSuccess(UserScheduleResponse userScheduleResponse) {
        view8.showUserScheduleByNameSuccess(userScheduleResponse);
    }

    @Override
    public void getUserScheduleByNameError(String error) {
        view8.showUserScheduleByNameError(error);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View8 view) {
        view8 = view;
    }

    @Override
    public void detachView() {

    }
}
