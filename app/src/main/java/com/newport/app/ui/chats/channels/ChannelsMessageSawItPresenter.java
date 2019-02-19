package com.newport.app.ui.chats.channels;

import com.newport.app.data.models.response.GenericResponse;

public class ChannelsMessageSawItPresenter implements ChannelsContract.CallbackMessageSawIt, ChannelsContract.PresenterMessageSawIt {

    private ChannelsContract.ViewMessageSawIt view;

    @Override
    public void sendMessageSawIt(int chat_id) {
        ChannelsIteractor.getMessageSawIt(chat_id, this);
    }

    @Override
    public void sendMessageSawItSuccess(GenericResponse genericResponse) {
        view.showSuccessMessageSawIt(genericResponse);
    }

    @Override
    public void sendMessageSawItError(String error) {
        view.showErrorMessageSawIt(error);
    }

    @Override
    public void sendMessageSawItFailure(String failure) {
        view.showErrorMessageSawIt(failure);
    }

    @Override
    public void attachedView(ChannelsContract.ViewMessageSawIt view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
