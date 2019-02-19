package com.newport.app.ui.chats.messages;

import com.newport.app.data.models.request.AttentionCalificationRequest;
import com.newport.app.data.models.response.GenericResponse;

public class ChatMessageAttentionCalificationPresenter implements ChatContract.CallbackAttentionCalification, ChatContract.PresenterAttentionCalification {

    private ChatContract.ViewAttentionCalification view;

    @Override
    public void setAttionCalification(AttentionCalificationRequest attentionCalificationRequest) {
        ChatIteractor.setAttentionCalification(attentionCalificationRequest, this);
    }

    @Override
    public void setAttentionCalificationSuccess(GenericResponse genericResponse) {
        view.showAttentionCalificationSuccess(genericResponse);
    }

    @Override
    public void setAttentionCalificationError(String error) {
        view.showAttentionCalificationError(error);
    }

    @Override
    public void setAttentionCalificationFailure(String error) {
        view.showAttentionCalificationError(error);
    }

    @Override
    public void attachedView(ChatContract.ViewAttentionCalification view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
