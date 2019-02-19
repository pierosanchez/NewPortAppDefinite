package com.newport.app.data.models.response;

public class ChatSendMessageResponse {
    private String response;
    private String date_message_sended;
    private int chat_id;

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
}
