package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.SwitchScheduleEmailResponse;

public class SwitchSchedule7Presenter implements SwitchScheduleContract.Presenter7, SwitchScheduleContract.CallBack7  {

    SwitchScheduleContract.View7 view7;

    @Override
    public void sendMailCoWoSwitchSchedule(String mailerAddress, String mailer, String toAddress, String to, String dayToChange, String mailerSchedule, int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {
        SwitchSchedule2Iteractor.sendMailCoWoSwitchSchedule(this, mailerAddress, mailer, toAddress, to, dayToChange, mailerSchedule, status, bossAddress, bossName, id, managerAddress, managerName);
    }

    @Override
    public void sendMailBossSwitchSchedule(String mailerAddress, String mailer, String toAddress, String to, String dayToChange, String mailerSchedule, int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {
        SwitchSchedule2Iteractor.sendMailBossSwitchSchedule(this, mailerAddress, mailer, toAddress, to, dayToChange, mailerSchedule, status, bossAddress, bossName, id, managerAddress, managerName);
    }

    @Override
    public void sendMailManagerSwitchSchedule(String mailerAddress, String mailer, String toAddress, String to, String dayToChange, String mailerSchedule, int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {
        SwitchSchedule2Iteractor.sendMailManagerSwitchSchedule(this, mailerAddress, mailer, toAddress, to, dayToChange, mailerSchedule, status, bossAddress, bossName, id, managerAddress, managerName);
    }

    @Override
    public void sendMailCoWoSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        view7.showSendMailCoWoSwitchScheduleSuccess(switchScheduleEmailResponse);
    }

    @Override
    public void sendMailCoWoSwitchScheduleError(String error) {
        view7.showSendMailCoWoSwitchScheduleError(error);
    }

    @Override
    public void sendMailBossSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        view7.showSendMailBossSwitchScheduleSuccess(switchScheduleEmailResponse);
    }

    @Override
    public void sendMailBossSwitchScheduleError(String error) {
        view7.showSendMailBossSwitchScheduleError(error);
    }

    @Override
    public void sendMailManagerSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        view7.showSendMailManagerSwitchScheduleSuccess(switchScheduleEmailResponse);
    }

    @Override
    public void sendMailManagerSwitchScheduleError(String error) {
        view7.showSendMailManagerSwitchScheduleError(error);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View7 view) {
        view7 = view;
    }

    @Override
    public void detachView() {

    }
}
