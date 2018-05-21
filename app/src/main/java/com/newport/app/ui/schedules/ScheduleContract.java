package com.newport.app.ui.schedules;

import com.newport.app.data.models.response.ScheduleResponse;
import com.newport.app.data.models.response.UserScheduleResponse;
import com.newport.app.util.BasePresenter;

import java.util.List;

/**
 * Created by tohure on 15/11/17.
 */

public interface ScheduleContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showSchedules(List<ScheduleResponse> scheduleResponseList);

        void showSchedulesError(String error);

        void showUserSchedules(UserScheduleResponse userScheduleResponse);

        void showUserSchedulesError(String Error);

    }

    interface Presenter extends BasePresenter<View> {

        void getSchedules();

        void getUserSchedules(String dni);

    }

    interface Callback {

        void getSchedulesSucces(List<ScheduleResponse> scheduleResponseList);

        void getUserSchedulesSuccess(UserScheduleResponse userScheduleResponse);

        void getSchedulesError(String error);

        void getSchedulesFailure(String message);

        void getUserSchedulesError(String error);

        void getUserSchedulesFailure(String message);
    }

}
