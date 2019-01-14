package com.newport.app.data.models.response;

public class ChatChannelResponse {

    private int id;
    private String channel_name;
    private int state;
    private String channel_owner_id;
    private String last_message_sended;
    private String user_initializer_chat_id;
    private int channel_id;
    private String user_initializer_chat_name;
    private MarcacionBean marcacion;
    private String last_message;

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MarcacionBean getMarcacion() {
        return marcacion;
    }

    public void setMarcacion(MarcacionBean marcacion) {
        this.marcacion = marcacion;
    }

    public String getChannel_owner_id() {
        return channel_owner_id;
    }

    public void setChannel_owner_id(String channel_owner_id) {
        this.channel_owner_id = channel_owner_id;
    }

    public String getLast_message_sended() {
        return last_message_sended;
    }

    public void setLast_message_sended(String last_message_sended) {
        this.last_message_sended = last_message_sended;
    }

    public String getUser_initializer_chat_id() {
        return user_initializer_chat_id;
    }

    public void setUser_initializer_chat_id(String user_initializer_chat_id) {
        this.user_initializer_chat_id = user_initializer_chat_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getUser_initializer_chat_name() {
        return user_initializer_chat_name;
    }

    public void setUser_initializer_chat_name(String user_initializer_chat_name) {
        this.user_initializer_chat_name = user_initializer_chat_name;
    }

    public static class MarcacionBean {
        /**
         * "marcacion": {
         *     "CODIGO": "00003950",
         *     "NOMBRE": "SANCHEZ ARBILDO PIERO ALEJANDRO"
         * }
         */
        private String CODIGO;
        private String NOMBRE;

        public String getCODIGO() {
            return CODIGO;
        }

        public void setCODIGO(String CODIGO) {
            this.CODIGO = CODIGO;
        }

        public String getNOMBRE() {
            return NOMBRE;
        }

        public void setNOMBRE(String NOMBRE) {
            this.NOMBRE = NOMBRE;
        }
    }
}
