package com.newport.app.data.models.response;

public class ChatChannelResponse {

    private int id;
    private String channel_name;
    private int state;
    private MarcacionBean marcacion;

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
