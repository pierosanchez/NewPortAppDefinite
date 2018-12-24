package com.newport.app.data.models.response;

import java.util.List;

public class ChatUserChatResponse {
    private int id;
    private String last_message_sended;
    private String user_initializer_chat_id;
    private int channel_id;
    private ChatChannelBean chat_channel;
    private List<UserChatMessagesBean> chat_messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ChatChannelBean getChat_channel() {
        return chat_channel;
    }

    public void setChat_channel(ChatChannelBean chat_channel) {
        this.chat_channel = chat_channel;
    }

    public List<UserChatMessagesBean> getChat_messages() {
        return chat_messages;
    }

    public void setChat_messages(List<UserChatMessagesBean> chat_messages) {
        this.chat_messages = chat_messages;
    }

    public static class ChatChannelBean {

        private int id;
        private String channel_name;
        private UserChatMarcacionBean marcacion;

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

        public UserChatMarcacionBean getMarcacion() {
            return marcacion;
        }

        public void setMarcacion(UserChatMarcacionBean marcacion) {
            this.marcacion = marcacion;
        }

        public static class UserChatMarcacionBean {

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

    public static class UserChatMessagesBean {

        private int id;
        private String message;
        private String user_id;
        private String date_message_sended;
        private String NOMBRE;
        private int chat_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDate_message_sended() {
            return date_message_sended;
        }

        public void setDate_message_sended(String date_message_sended) {
            this.date_message_sended = date_message_sended;
        }

        public int getChat_id() {
            return chat_id;
        }

        public void setChat_id(int chat_id) {
            this.chat_id = chat_id;
        }

        public String getNOMBRE() {
            return NOMBRE;
        }

        public void setNOMBRE(String NOMBRE) {
            this.NOMBRE = NOMBRE;
        }
    }
}
