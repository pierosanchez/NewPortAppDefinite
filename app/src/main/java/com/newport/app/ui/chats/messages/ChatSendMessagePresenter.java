package com.newport.app.ui.chats.messages;

import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.response.ChatSendMessageResponse;

public class ChatSendMessagePresenter implements ChatContract.PresenterSendMessage, ChatContract.CallBackSendMessage {

    private ChatContract.ViewSendMessage view;

    @Override
    public void sendMessage(ChatRequest chatRequest) {
        ChatIteractor.sengMessage(chatRequest, this);
    }

    @Override
    public void sendChatMessageSuccess(ChatSendMessageResponse chatSendMessageResponse) {
        view.showSendMessageSuccess(chatSendMessageResponse);
    }

    @Override
    public void sendChatMessageError(String error) {
        view.showSendMessageError(error);
    }

    @Override
    public void sendChatMessageFailure(String failure) {
        view.showSendMessageError(failure);
    }

    @Override
    public void attachedView(ChatContract.ViewSendMessage view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
