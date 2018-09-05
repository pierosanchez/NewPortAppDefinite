package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.UserScheduleResponse;

public class SwitchSchedule2Presenter implements SwitchScheduleContract.Presenter2, SwitchScheduleContract.CallBack2 {

    SwitchScheduleContract.View2 view2;

    @Override
    public void getUserSchedule() {
        view2.showLoading();
        SwitchSchedule2Iteractor.getUserSchedules(this);
    }

    @Override
    public void getUserSchedulesSuccess(UserScheduleResponse userScheduleResponse) {
        view2.showUserSchedulesSuccess(userScheduleResponse);
        view2.hideLoading();
    }

    @Override
    public void getUserScheduleError(String error) {
        view2.showUserSchedulesError(error);
        view2.hideLoading();
    }

    @Override
    public void attachedView(SwitchScheduleContract.View2 view) {
        this.view2 = view;
    }

    @Override
    public void detachView() {

    }
}
