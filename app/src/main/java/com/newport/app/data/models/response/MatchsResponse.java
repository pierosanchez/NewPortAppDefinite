package com.newport.app.data.models.response;

public class MatchsResponse {
    private String partido;
    private String fecha;
    private String hora;
    private String id_partido;
    private String pais1;
    private String pais2;
    private String pais1_image;
    private String pais2_image;

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId_partido() {
        return id_partido;
    }

    public void setId_partido(String id_partido) {
        this.id_partido = id_partido;
    }

    public String getPais1() {
        return pais1;
    }

    public void setPais1(String pais1) {
        this.pais1 = pais1;
    }

    public String getPais2() {
        return pais2;
    }

    public void setPais2(String pais2) {
        this.pais2 = pais2;
    }

    public String getPais1_image() {
        return pais1_image;
    }

    public void setPais1_image(String pais1_image) {
        this.pais1_image = pais1_image;
    }

    public String getPais2_image() {
        return pais2_image;
    }

    public void setPais2_image(String pais2_image) {
        this.pais2_image = pais2_image;
    }
}
