package com.newport.app.data.models.request;

public class UserRegisterRequest {
    private String cod_sap;
    private String mail;
    private String password_user;
    private String firebase_token;

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getCod_sap() {
        return cod_sap;
    }

    public void setCod_sap(String cod_sap) {
        this.cod_sap = cod_sap;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }
}
