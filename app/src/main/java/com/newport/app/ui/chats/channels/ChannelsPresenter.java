package com.newport.app.ui.chats.channels;

import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.ui.events.EventsContract;

import java.util.List;

public class ChannelsPresenter implements ChannelsContract.Presenter, ChannelsContract.CallBack {

    private ChannelsContract.View view;

    @Override
    public void getCHannels() {
        view.showLoading();
        ChannelsIteractor.getChannels(this);
    }

    @Override
    public void getChannelsSuccess(List<ChatChannelResponse> channelResponseList) {
        view.HideLoading();
        view.showChannels(channelResponseList);
    }

    @Override
    public void getChannelsError(String error) {
        view.HideLoading();
        view.showChannelsError(error);
    }

    @Override
    public void getChannelsFailure(String failure) {
        view.HideLoading();
        view.showChannelsError(failure);
    }

    @Override
    public void attachedView(ChannelsContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
