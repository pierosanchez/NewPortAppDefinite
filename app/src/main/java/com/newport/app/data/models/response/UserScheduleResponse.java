package com.newport.app.data.models.response;

public class UserScheduleResponse {

    /**
     * horario":"00:00-09:00"
     * colaborador:"Juan Lucano"}]
     */

    private String cod_sap;
    private String user_name;
    private String user_schedule;
    private String Lun;
    private String Mar;
    private String Mie;
    private String Jue;
    private String Vie;
    private String Sab;
    private String Dom;
    private String EMAIL;

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getCod_sap() {
        return cod_sap;
    }

    public void setCod_sap(String cod_sap) {
        this.cod_sap = cod_sap;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_schedule() {
        return user_schedule;
    }

    public void setUser_schedule(String user_schedule) {
        this.user_schedule = user_schedule;
    }

    public String getLun() {
        return Lun;
    }

    public void setLun(String lun) {
        Lun = lun;
    }

    public String getMar() {
        return Mar;
    }

    public void setMar(String mar) {
        Mar = mar;
    }

    public String getMie() {
        return Mie;
    }

    public void setMie(String mie) {
        Mie = mie;
    }

    public String getJue() {
        return Jue;
    }

    public void setJue(String jue) {
        Jue = jue;
    }

    public String getVie() {
        return Vie;
    }

    public void setVie(String vie) {
        Vie = vie;
    }

    public String getSab() {
        return Sab;
    }

    public void setSab(String sab) {
        Sab = sab;
    }

    public String getDom() {
        return Dom;
    }

    public void setDom(String dom) {
        Dom = dom;
    }
}
