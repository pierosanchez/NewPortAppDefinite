package com.newport.app.ui.chats.messages;

import android.support.annotation.NonNull;
import android.util.Log;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.request.AttentionCalificationRequest;
import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.response.ChatSendMessageResponse;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.util.PreferencesHeper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatIteractor {
    static void getChatUserChat(final ChatContract.CallBack callback) {
        Call<ChatUserChatResponse> call = NewPortApiManager.apiManager().getUserChatMessages(
                Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext()))
        );

        call.enqueue(new Callback<ChatUserChatResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<ChatUserChatResponse> call, @NonNull Response<ChatUserChatResponse> response) {
                if (response.isSuccessful()) {
                    callback.getChatUserChatSuccess(response.body());
                } else {
                    callback.getChatUserChatError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ChatUserChatResponse> call, @NonNull Throwable t) {
                callback.getChatUserChatFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void sengMessage(ChatRequest chatRequest, final ChatContract.CallBackSendMessage callback) {
        Call<ChatSendMessageResponse> call = NewPortApiManager.apiManager().sendMessage(chatRequest);

        call.enqueue(new Callback<ChatSendMessageResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<ChatSendMessageResponse> call, @NonNull Response<ChatSendMessageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse() != null) {
                        if (response.body().getResponse().isEmpty()) {
                            callback.sendChatMessageError("Ocurrió un error al enviar el mensaje");

                        } else {
                            if (response.body().getResponse().equals("success")) {
                                callback.sendChatMessageSuccess(response.body());
                            } else {
                                callback.sendChatMessageError("Ocurrió un error al enviar el mensaje");
                            }
                        }
                    }
                } else {
                    callback.sendChatMessageError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ChatSendMessageResponse> call, @NonNull Throwable t) {
                callback.sendChatMessageFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void setMessageTerminated(int chat_id, final ChatContract.CallbackSetMessageTerminated callback) {
        Call<GenericResponse> call = NewPortApiManager.apiManager().setMessageTerminated(chat_id);

        call.enqueue(new Callback<GenericResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        callback.SetMessageTerminatedSuccess(response.body());

                    } else {
                        callback.SetMessageTerminatedError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                    }
                } else {
                    callback.SetMessageTerminatedError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                callback.SetMessageTerminatedFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    static void setAttentionCalification(AttentionCalificationRequest attentionCalificationRequest, final ChatContract.CallbackAttentionCalification callback) {
        Call<GenericResponse> call = NewPortApiManager.apiManager().setAttentionCalification(attentionCalificationRequest);

        call.enqueue(new Callback<GenericResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        callback.setAttentionCalificationSuccess(response.body());

                    } else {
                        callback.setAttentionCalificationError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                    }
                } else {
                    callback.setAttentionCalificationError(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
                }

            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                callback.setAttentionCalificationFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}
