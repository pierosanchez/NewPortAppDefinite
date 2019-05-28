package com.newport.app.data.models.response;

public class ChatSendMessageResponse {
    private String response;
    private String date_message_sended;
    private int chat_id;
    private int status_chat;
    private int status_message;

    public int getStatus_chat() {
        return status_chat;
    }

    public void setStatus_chat(int status_chat) {
        this.status_chat = status_chat;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDate_message_sended() {
        return date_message_sended;
    }

    public void setDate_message_sended(String date_message_sended) {
        this.date_message_sended = date_message_sended;
    }

    public int getStatus_message() {
        return status_message;
    }

    public void setStatus_message(int status_message) {
        this.status_message = status_message;
    }
}
