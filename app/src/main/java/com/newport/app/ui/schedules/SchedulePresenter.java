package com.newport.app.ui.schedules;

import com.newport.app.data.models.response.ScheduleResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;

/**
 * Created by tohure on 15/11/17.
 */

public class SchedulePresenter implements ScheduleContract.Presenter, ScheduleContract.Callback {

    private ScheduleContract.View view;

    @Override
    public void getSchedules() {
        view.showLoading();
        ScheduleInteractor.getSchedules(this);
    }

    @Override
    public void getUserSchedules(String dni) {
        view.showLoading();
        ScheduleInteractor.getUserSchedules(dni, this);
    }

    @Override
    public void attachedView(ScheduleContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getSchedulesSucces(List<ScheduleResponse> scheduleResponseList) {
        view.showSchedules(scheduleResponseList);
        view.hideLoading();
    }

    @Override
    public void getUserSchedulesSuccess(UserScheduleResponse userScheduleResponse) {
        view.showUserSchedules(userScheduleResponse);
    }

    @Override
    public void getSchedulesError(String error) {
        view.showSchedulesError(error);
        view.hideLoading();
    }

    @Override
    public void getSchedulesFailure(String message) {
        view.showSchedulesError(message);
        view.hideLoading();
    }

    @Override
    public void getUserSchedulesError(String error) {
        view.showUserSchedulesError(error);
        view.hideLoading();
    }

    @Override
    public void getUserSchedulesFailure(String message) {
        view.showUserSchedulesError(message);
        view.hideLoading();
    }
}
