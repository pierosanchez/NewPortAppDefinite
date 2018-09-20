package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;

import java.util.List;

public class SwitchSchedule6Presenter implements SwitchScheduleContract.Presenter6, SwitchScheduleContract.CallBack6 {

    SwitchScheduleContract.View6 view6;

    @Override
    public void getUserSwitchSchedulePendingRequests(String emailUser) {
        SwitchSchedule2Iteractor.getUserSwitchSchedulePendingRequests(this, emailUser);
    }

    @Override
    public void getBossSwitchSchedulePendingRequests(String emailBoss) {
        SwitchSchedule2Iteractor.getBossUserSwitchSchedulePendingRequests(this, emailBoss);
    }

    @Override
    public void getManagerSwitchSchedulePendingRequests(String emailBoss) {
        SwitchSchedule2Iteractor.getManagerUserSwitchSchedulePendingRequests(this, emailBoss);
    }

    @Override
    public void getUserSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        view6.showUserSwitchSchedulePendingRequestsSuccess(switchScheduleEmailResponseList);
    }

    @Override
    public void getUserSwitchSchedulePendingRequestsError(String error) {
        view6.showUserSwitchSchedulePendingRequestsError(error);
    }

    @Override
    public void getBossSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        view6.showBossSwitchSchedulePendingRequestsSuccess(switchScheduleEmailResponseList);
    }

    @Override
    public void getBossSwitchSchedulePendingRequestsError(String error) {
        view6.showBossSwitchSchedulePendingRequestsError(error);
    }

    @Override
    public void getManagerSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        view6.showManagerSwitchSchedulePendingRequestsSuccess(switchScheduleEmailResponseList);
    }

    @Override
    public void getManagerSwitchSchedulePendingRequestsError(String error) {
        view6.showManagerSwitchSchedulePendingRequestsError(error);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View6 view) {
        this.view6 = view;
    }

    @Override
    public void detachView() {

    }
}
