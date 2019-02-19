package com.newport.app.ui.chats.messages;

import com.newport.app.data.models.response.GenericResponse;

public class ChatMessageTerminatedPresenter implements ChatContract.CallbackSetMessageTerminated, ChatContract.PresenterSetMessageTerminated {

    private ChatContract.ViewSetMessageTerminated view;

    @Override
    public void setMessageTerminated(int chat_id) {
        ChatIteractor.setMessageTerminated(chat_id, this);
    }

    @Override
    public void SetMessageTerminatedSuccess(GenericResponse genericResponse) {
        view.showSetMessageTerminatedSuccess(genericResponse);
    }

    @Override
    public void SetMessageTerminatedError(String error) {
        view.ShowSetMessageTerminatedError(error);
    }

    @Override
    public void SetMessageTerminatedFailure(String failure) {
        view.ShowSetMessageTerminatedError(failure);
    }

    @Override
    public void attachedView(ChatContract.ViewSetMessageTerminated view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
