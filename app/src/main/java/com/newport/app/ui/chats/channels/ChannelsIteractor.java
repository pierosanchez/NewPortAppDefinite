package com.newport.app.ui.chats.channels;

import android.support.annotation.NonNull;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelsIteractor {
    static void getChannels(final ChannelsContract.CallBack callback) {
        Call<List<ChatChannelResponse>> call = NewPortApiManager.apiManager().getChannels(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext()));

        call.enqueue(new Callback<List<ChatChannelResponse>>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<List<ChatChannelResponse>> call, @NonNull Response<List<ChatChannelResponse>> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {

                        callback.getChannelsSuccess(response.body());

                    } else {
                        callback.getChannelsError(NewPortApplication.getAppContext().getString(R.string.no_chats_response));
                    }
                } else {
                    callback.getChannelsError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ChatChannelResponse>> call, @NonNull Throwable t) {
                callback.getChannelsFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void getMessageSawIt(int chat_id, final ChannelsContract.CallbackMessageSawIt callback) {
        Call<GenericResponse> call = NewPortApiManager.apiManager().sendMessageSawit(chat_id);

        call.enqueue(new Callback<GenericResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.sendMessageSawItSuccess(response.body());
                    } else {
                        callback.sendMessageSawItError(NewPortApplication.getAppContext().getString(R.string.no_chats_response));
                    }
                } else {
                    callback.sendMessageSawItError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                callback.sendMessageSawItFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
