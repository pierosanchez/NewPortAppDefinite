package com.newport.app.ui.scheduleprocess;

import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.data.models.response.UserScheduleResponse;
import com.newport.app.util.BasePresenter;

import java.util.List;

public interface SwitchScheduleContract {

    interface View {
        void showLoading();

        void hideLoading();

        void showScheduleData(UserResponse userResponse);

        void showScheduleDataError(String error);
    }

    interface Presenter extends BasePresenter<View>{
        void getScheduleData();
    }

    interface Callback {
        void getScheduleDataSuccess(UserResponse userResponse);

        void getScheduleDataError(String error);
    }


    interface View2 {
        void showLoading();

        void hideLoading();

        void showUserSchedulesSuccess(UserScheduleResponse userScheduleResponse);

        void showUserSchedulesError(String error);
    }

    interface Presenter2 extends BasePresenter<View2> {
        void getUserSchedule();
    }

    interface CallBack2 {
        void getUserSchedulesSuccess(UserScheduleResponse userScheduleResponse);

        void getUserScheduleError(String error);
    }


    interface View3 {
        void showUsersOffSuccess(List<UserScheduleResponse> userScheduleResponseList);

        void ShowUsersOffError(String error);
    }

    interface Presenter3 extends BasePresenter<View3> {
        void getUsersOff(String dayOff);
    }

    interface Callback3 {
        void getUsersOffSuccess(List<UserScheduleResponse> userScheduleResponseList);

        void getUsersOffError(String error);
    }


    interface View4 {
        void showUserWorkSuccess(List<UserScheduleResponse> userScheduleResponseList);

        void showUsersWorkError(String error);
    }

    interface Presenter4 extends BasePresenter<View4> {
        void getUsersWork(String dayWork);
    }

    interface CallBack4 {
        void getUserWorkSuccess(List<UserScheduleResponse> userScheduleResponseList);

        void getUserWorkError(String error);
    }

    interface View5 {
        void showSendMailSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void showSendMailSwitchScheduleError(String error);
    }

    interface Presenter5 extends BasePresenter<View5> {
        void sendMailSwitchSchedule(String mailerAddress, String mailer, String toAddress,
                                    String to, String dayToChange, String mailerSchedule, String otherUserSchedule,
                                    int status, /*String bossAddress, String bossName, String managerAddress, String managerName,*/ String sala, String area);
    }

    interface CallBack5 {
        void sendMailSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void sendMailSwitchScheduleError(String error);
    }


    interface View6 {
        void showUserSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void showUserSwitchSchedulePendingRequestsError(String error);

        void showBossSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void showBossSwitchSchedulePendingRequestsError(String error);

        void showManagerSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void showManagerSwitchSchedulePendingRequestsError(String error);
    }

    interface Presenter6 extends BasePresenter<View6> {
        void getUserSwitchSchedulePendingRequests(String emailUser);

        void getBossSwitchSchedulePendingRequests(String emailBoss);

        void getManagerSwitchSchedulePendingRequests(String emailBoss);
    }

    interface CallBack6 {
        void getUserSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void getUserSwitchSchedulePendingRequestsError(String error);

        void getBossSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void getBossSwitchSchedulePendingRequestsError(String error);

        void getManagerSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList);
        void getManagerSwitchSchedulePendingRequestsError(String error);
    }


    interface View7 {
        void showSendMailCoWoSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void showSendMailCoWoSwitchScheduleError(String error);

        void showSendMailBossSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void showSendMailBossSwitchScheduleError(String error);

        void showSendMailManagerSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void showSendMailManagerSwitchScheduleError(String error);
    }

    interface Presenter7 extends BasePresenter<View7> {
        void sendMailCoWoSwitchSchedule(String mailerAddress,
                                        String mailer, String toAddress, String to,
                                        String dayToChange, String mailerSchedule,
                                        int status, String bossAddress, String bossName, int id,
                                        String managerAddress, String managerName);

        void sendMailBossSwitchSchedule(String mailerAddress,
                                        String mailer, String toAddress, String to,
                                        String dayToChange, String mailerSchedule,
                                        int status, String bossAddress, String bossName, int id,
                                        String managerAddress, String managerName);

        void sendMailManagerSwitchSchedule(String mailerAddress,
                                        String mailer, String toAddress, String to,
                                        String dayToChange, String mailerSchedule,
                                        int status, String bossAddress, String bossName, int id,
                                        String managerAddress, String managerName);
    }

    interface CallBack7 {
        void sendMailCoWoSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void sendMailCoWoSwitchScheduleError(String error);

        void sendMailBossSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void sendMailBossSwitchScheduleError(String error);

        void sendMailManagerSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse);
        void sendMailManagerSwitchScheduleError(String error);
    }


    interface View8 {
        void showUserScheduleByNameSuccess(UserScheduleResponse userScheduleResponse);
        void showUserScheduleByNameError(String error);
    }

    interface Presenter8 extends BasePresenter<View8> {
        void getUserScheduleByName(String user_name);
    }

    interface CallBack8 {
        void getUserScheduleByNameSuccess(UserScheduleResponse userScheduleResponse);
        void getUserScheduleByNameError(String error);
    }
}
