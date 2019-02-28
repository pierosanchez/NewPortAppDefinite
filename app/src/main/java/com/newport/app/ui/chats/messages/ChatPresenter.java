package com.newport.app.ui.chats.messages;

import com.newport.app.data.models.response.ChatUserChatResponse;

public class ChatPresenter implements ChatContract.Presenter, ChatContract.CallBack {

    private ChatContract.View view;

    @Override
    public void getChatUserChat(int chat_id) {
        view.showLoading();
        ChatIteractor.getChatUserChat(chat_id, this);
    }

    @Override
    public void getChatUserChatSuccess(ChatUserChatResponse chatUserChatResponse) {
        view.hideLoading();
        view.showChatSuccess(chatUserChatResponse);
    }

    @Override
    public void getChatUserChatError(String error) {
        view.hideLoading();
        view.showChatsError(error);
    }

    @Override
    public void getChatUserChatFailure(String failure) {
        view.hideLoading();
        view.showChatsError(failure);
    }

    @Override
    public void attachedView(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
