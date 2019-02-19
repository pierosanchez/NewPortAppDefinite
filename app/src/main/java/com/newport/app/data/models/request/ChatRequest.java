package com.newport.app.data.models.request;

public class ChatRequest {

    private String message;
    private String userId;
    private int chatid;
    private int channelId;
    private String user_initializer_chat_id;

    public String getUser_initializer_chat_id() {
        return user_initializer_chat_id;
    }

    public void setUser_initializer_chat_id(String user_initializer_chat_id) {
        this.user_initializer_chat_id = user_initializer_chat_id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }
}
