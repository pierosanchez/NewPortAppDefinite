package com.newport.app.ui.chats.channels;

import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.util.BasePresenter;

import java.util.List;

public interface ChannelsContract {

    //All related to chat's channels view
    interface View {
        void showLoading();
        void HideLoading();
        void showChannels(List<ChatChannelResponse> channelResponseList);
        void showChannelsError(String error);
    }

    //All related to chat's channels presenter
    interface Presenter extends BasePresenter<View> {
        void getCHannels();
    }

    //All related to chat's channels Callback
    interface CallBack {
        void getChannelsSuccess(List<ChatChannelResponse> channelResponseList);
        void getChannelsError(String error);
        void getChannelsFailure(String failure);
    }

}
