package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.SwitchScheduleEmailResponse;

public class SwitchSchedule5Presenter implements SwitchScheduleContract.Presenter5, SwitchScheduleContract.CallBack5 {

    SwitchScheduleContract.View5 view5;

    @Override
    public void sendMailSwitchSchedule(String mailerAddress, String mailer, String toAddress,
                                       String to, String dayToChange, String mailerSchedule, String otherUserSchedule,
                                       int status/*, String bossAddress, String bossName, String managerAddress, String managerName*/, String sala, String area) {
        SwitchSchedule2Iteractor.sendMailSwitchSchedule(this, mailerAddress,
                mailer, toAddress, to, dayToChange, mailerSchedule, otherUserSchedule, status, /*bossAddress, bossName, managerAddress, managerName,*/ sala, area);
    }

    @Override
    public void attachedView(SwitchScheduleContract.View5 view) {
        view5 = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void sendMailSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        view5.showSendMailSwitchScheduleSuccess(switchScheduleEmailResponse);
    }

    @Override
    public void sendMailSwitchScheduleError(String error) {
        view5.showSendMailSwitchScheduleError(error);
    }
}
