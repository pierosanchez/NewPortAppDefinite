package com.newport.app.ui.scheduleprocess;

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
}
