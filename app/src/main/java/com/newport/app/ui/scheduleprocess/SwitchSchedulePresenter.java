package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.UserResponse;

public class SwitchSchedulePresenter implements SwitchScheduleContract.Presenter, SwitchScheduleContract.Callback{

    SwitchScheduleContract.View view;

    @Override
    public void getScheduleData() {
        view.showLoading();
        SwitchScheduleIteractor.getScheduleData(this);
    }

    @Override
    public void getScheduleDataSuccess(UserResponse userResponse) {
        view.showScheduleData(userResponse);
        view.hideLoading();
    }

    @Override
    public void getScheduleDataError(String error) {
        view.showScheduleDataError(error);
        view.hideLoading();
    }

    @Override
    public void attachedView(SwitchScheduleContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }
}
