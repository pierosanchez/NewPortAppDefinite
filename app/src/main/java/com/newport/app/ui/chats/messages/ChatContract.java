package com.newport.app.ui.chats.messages;

import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.response.ChatSendMessageResponse;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.util.BasePresenter;

public interface ChatContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showChatSuccess(ChatUserChatResponse chatUserChatResponse);
        void showChatsError(String error);
    }

    interface Presenter extends BasePresenter<View> {
        void getChatUserChat();
    }

    interface CallBack {
        void getChatUserChatSuccess(ChatUserChatResponse chatUserChatResponse);
        void getChatUserChatError(String error);
        void getChatUserChatFailure(String failure);
    }

    /*
        this part is for the send message service
     */

    interface ViewSendMessage {
        void showSendMessageSuccess(ChatSendMessageResponse chatSendMessageResponse);
        void showSendMessageError(String error);
    }

    interface PresenterSendMessage extends BasePresenter<ViewSendMessage> {
        void sendMessage(ChatRequest chatRequest);
    }

    interface CallBackSendMessage {
        void sendChatMessageSuccess(ChatSendMessageResponse chatSendMessageResponse);
        void sendChatMessageError(String error);
        void sendChatMessageFailure(String failure);
    }
}
