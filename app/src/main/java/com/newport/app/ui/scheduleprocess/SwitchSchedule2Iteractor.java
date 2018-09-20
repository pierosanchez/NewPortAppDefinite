package com.newport.app.ui.scheduleprocess;

import android.support.annotation.NonNull;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.request.SwitchScheduleEmailRequest;
import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;
import com.newport.app.data.models.response.UserScheduleResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchSchedule2Iteractor {

    @AddTrace(name = "getUserSchedules")
    static void getUserSchedules(final SwitchScheduleContract.CallBack2 callBack2) {
        String currentSapCode = PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext());

        Call<UserScheduleResponse> call = NewPortApiManager.apiManager().getUserSchedules(currentSapCode);

        call.enqueue(new Callback<UserScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserScheduleResponse> call, @NonNull Response<UserScheduleResponse> response) {
                if (response.isSuccessful()) {
                    callBack2.getUserSchedulesSuccess(response.body());
                } else {
                    callBack2.getUserScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserScheduleResponse> call, @NonNull Throwable t) {
                callBack2.getUserScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getUserOff")
    static void getUserOff(final SwitchScheduleContract.Callback3 callBack3, String dayOff) {

        Call<List<UserScheduleResponse>> call = NewPortApiManager.apiManager().getUsersOff(dayOff);

        call.enqueue(new Callback<List<UserScheduleResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Response<List<UserScheduleResponse>> response) {
                if (response.isSuccessful()) {
                    callBack3.getUsersOffSuccess(response.body());
                } else {
                    callBack3.getUsersOffError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Throwable t) {
                callBack3.getUsersOffError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getUserWork")
    static void getUserWork(final SwitchScheduleContract.CallBack4 callBack4, String dayWork) {

        Call<List<UserScheduleResponse>> call = NewPortApiManager.apiManager().getUserWork(dayWork);

        call.enqueue(new Callback<List<UserScheduleResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Response<List<UserScheduleResponse>> response) {
                if (response.isSuccessful()) {
                    callBack4.getUserWorkSuccess(response.body());
                } else {
                    callBack4.getUserWorkError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserScheduleResponse>> call, @NonNull Throwable t) {
                callBack4.getUserWorkError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "sendMailSwitchSchedule")
    static void sendMailSwitchSchedule(final SwitchScheduleContract.CallBack5 callBack5, String mailerAddress,
                            String mailer, String toAddress, String to,
                            String dayToChange, String mailerSchedule, String otherUserSchedule,
                            int status, /*String bossAddress, String bossName, String managerAddress, String managerName,*/ String sala, String area) {

        SwitchScheduleEmailRequest switchScheduleEmailRequest = new SwitchScheduleEmailRequest();

        switchScheduleEmailRequest.setMailerAddress(mailerAddress);
        switchScheduleEmailRequest.setMailer(mailer);
        switchScheduleEmailRequest.setToAddress(toAddress);
        switchScheduleEmailRequest.setTo(to);
        switchScheduleEmailRequest.setDayToChange(dayToChange);
        switchScheduleEmailRequest.setScheduleSecondUser(otherUserSchedule);
        switchScheduleEmailRequest.setSchedule(mailerSchedule);
        switchScheduleEmailRequest.setStatus(status);
        /*switchScheduleEmailRequest.setBossAddress(bossAddress);
        switchScheduleEmailRequest.setBossName(bossName);
        switchScheduleEmailRequest.setManagerAddress(managerAddress);
        switchScheduleEmailRequest.setManagerName(managerName);*/
        switchScheduleEmailRequest.setSala(sala);
        switchScheduleEmailRequest.setArea(area);

        Call<SwitchScheduleEmailResponse> call = NewPortApiManager.apiManager().sendEmailSwitchSchedule(switchScheduleEmailRequest);

        call.enqueue(new Callback<SwitchScheduleEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Response<SwitchScheduleEmailResponse> response) {
                if (response.isSuccessful()) {
                    callBack5.sendMailSwitchScheduleSuccess(response.body());
                } else {
                    callBack5.sendMailSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Throwable t) {
                callBack5.sendMailSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getUserSwitchSchedulePendingRequests")
    static void getUserSwitchSchedulePendingRequests(final SwitchScheduleContract.CallBack6 callBack6, String emailUser) {

        Call<List<SwitchSchedulesPendingRequestResponse>> call = NewPortApiManager.apiManager().getUserSwitchSchedulePendingRequests(emailUser);

        call.enqueue(new Callback<List<SwitchSchedulesPendingRequestResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Response<List<SwitchSchedulesPendingRequestResponse>> response) {
                if (response.isSuccessful()) {
                    callBack6.getUserSwitchSchedulePendingRequestsSuccess(response.body());
                } else {
                    callBack6.getUserSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Throwable t) {
                callBack6.getUserSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getBossUserSwitchSchedulePendingRequests")
    static void getBossUserSwitchSchedulePendingRequests(final SwitchScheduleContract.CallBack6 callBack6, String emailBoss) {

        Call<List<SwitchSchedulesPendingRequestResponse>> call = NewPortApiManager.apiManager().getBossUserSwitchSchedulePendingRequests(emailBoss);

        call.enqueue(new Callback<List<SwitchSchedulesPendingRequestResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Response<List<SwitchSchedulesPendingRequestResponse>> response) {
                if (response.isSuccessful()) {
                    callBack6.getBossSwitchSchedulePendingRequestsSuccess(response.body());
                } else {
                    callBack6.getBossSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Throwable t) {
                callBack6.getBossSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getManagerUserSwitchSchedulePendingRequests")
    static void getManagerUserSwitchSchedulePendingRequests(final SwitchScheduleContract.CallBack6 callBack6, String managers_email) {

        Call<List<SwitchSchedulesPendingRequestResponse>> call = NewPortApiManager.apiManager().getManagerUserSwitchSchedulePendingRequests(managers_email);

        call.enqueue(new Callback<List<SwitchSchedulesPendingRequestResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Response<List<SwitchSchedulesPendingRequestResponse>> response) {
                if (response.isSuccessful()) {
                    callBack6.getManagerSwitchSchedulePendingRequestsSuccess(response.body());
                } else {
                    callBack6.getManagerSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SwitchSchedulesPendingRequestResponse>> call, @NonNull Throwable t) {
                callBack6.getManagerSwitchSchedulePendingRequestsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "sendMailCoWoSwitchSchedule")
    static void sendMailCoWoSwitchSchedule(final SwitchScheduleContract.CallBack7 callBack7, String mailerAddress,
                                       String mailer, String toAddress, String to,
                                       String dayToChange, String mailerSchedule,
                                       int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {

        SwitchScheduleEmailRequest switchScheduleEmailRequest = new SwitchScheduleEmailRequest();

        switchScheduleEmailRequest.setMailerAddress(mailerAddress);
        switchScheduleEmailRequest.setMailer(mailer);
        switchScheduleEmailRequest.setToAddress(toAddress);
        switchScheduleEmailRequest.setTo(to);
        switchScheduleEmailRequest.setDayToChange(dayToChange);
        switchScheduleEmailRequest.setSchedule(mailerSchedule);
        switchScheduleEmailRequest.setStatus(status);
        switchScheduleEmailRequest.setBossAddress(bossAddress);
        switchScheduleEmailRequest.setBossName(bossName);
        switchScheduleEmailRequest.setId(id);
        switchScheduleEmailRequest.setManagerAddress(managerAddress);
        switchScheduleEmailRequest.setManagerName(managerName);

        Call<SwitchScheduleEmailResponse> call = NewPortApiManager.apiManager().sendMailCoWoSwitchSchedule(switchScheduleEmailRequest);

        call.enqueue(new Callback<SwitchScheduleEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Response<SwitchScheduleEmailResponse> response) {
                if (response.isSuccessful()) {
                    callBack7.sendMailCoWoSwitchScheduleSuccess(response.body());
                } else {
                    callBack7.sendMailCoWoSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Throwable t) {
                callBack7.sendMailCoWoSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "sendMailBossSwitchSchedule")
    static void sendMailBossSwitchSchedule(final SwitchScheduleContract.CallBack7 callBack7, String mailerAddress,
                                           String mailer, String toAddress, String to,
                                           String dayToChange, String mailerSchedule,
                                           int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {

        SwitchScheduleEmailRequest switchScheduleEmailRequest = new SwitchScheduleEmailRequest();

        switchScheduleEmailRequest.setMailerAddress(mailerAddress);
        switchScheduleEmailRequest.setMailer(mailer);
        switchScheduleEmailRequest.setToAddress(toAddress);
        switchScheduleEmailRequest.setTo(to);
        switchScheduleEmailRequest.setDayToChange(dayToChange);
        switchScheduleEmailRequest.setSchedule(mailerSchedule);
        switchScheduleEmailRequest.setStatus(status);
        switchScheduleEmailRequest.setBossAddress(bossAddress);
        switchScheduleEmailRequest.setBossName(bossName);
        switchScheduleEmailRequest.setId(id);
        switchScheduleEmailRequest.setManagerAddress(managerAddress);
        switchScheduleEmailRequest.setManagerName(managerName);

        Call<SwitchScheduleEmailResponse> call = NewPortApiManager.apiManager().sendMailBossSwitchSchedule(switchScheduleEmailRequest);

        call.enqueue(new Callback<SwitchScheduleEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Response<SwitchScheduleEmailResponse> response) {
                if (response.isSuccessful()) {
                    callBack7.sendMailBossSwitchScheduleSuccess(response.body());
                } else {
                    callBack7.sendMailBossSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Throwable t) {
                callBack7.sendMailBossSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "sendMailManagerSwitchSchedule")
    static void sendMailManagerSwitchSchedule(final SwitchScheduleContract.CallBack7 callBack7, String mailerAddress,
                                           String mailer, String toAddress, String to,
                                           String dayToChange, String mailerSchedule,
                                           int status, String bossAddress, String bossName, int id, String managerAddress, String managerName) {

        SwitchScheduleEmailRequest switchScheduleEmailRequest = new SwitchScheduleEmailRequest();

        switchScheduleEmailRequest.setMailerAddress(mailerAddress);
        switchScheduleEmailRequest.setMailer(mailer);
        switchScheduleEmailRequest.setToAddress(toAddress);
        switchScheduleEmailRequest.setTo(to);
        switchScheduleEmailRequest.setDayToChange(dayToChange);
        switchScheduleEmailRequest.setSchedule(mailerSchedule);
        switchScheduleEmailRequest.setStatus(status);
        switchScheduleEmailRequest.setBossAddress(bossAddress);
        switchScheduleEmailRequest.setBossName(bossName);
        switchScheduleEmailRequest.setId(id);
        switchScheduleEmailRequest.setManagerAddress(managerAddress);
        switchScheduleEmailRequest.setManagerName(managerName);

        Call<SwitchScheduleEmailResponse> call = NewPortApiManager.apiManager().sendMailManagerSwitchSchedule(switchScheduleEmailRequest);

        call.enqueue(new Callback<SwitchScheduleEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Response<SwitchScheduleEmailResponse> response) {
                if (response.isSuccessful()) {
                    callBack7.sendMailManagerSwitchScheduleSuccess(response.body());
                } else {
                    callBack7.sendMailManagerSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SwitchScheduleEmailResponse> call, @NonNull Throwable t) {
                callBack7.sendMailManagerSwitchScheduleError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getUserScheduleByName")
    static void getUserScheduleByName(final SwitchScheduleContract.CallBack8 callBack8, String user_name) {

        Call<UserScheduleResponse> call = NewPortApiManager.apiManager().getUserScheduleByName(user_name);

        call.enqueue(new Callback<UserScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserScheduleResponse> call, @NonNull Response<UserScheduleResponse> response) {
                if (response.isSuccessful()) {
                    callBack8.getUserScheduleByNameSuccess(response.body());
                } else {
                    callBack8.getUserScheduleByNameError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserScheduleResponse> call, @NonNull Throwable t) {
                callBack8.getUserScheduleByNameError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
