package com.newport.app.data.models.response;

public class SwitchSchedulesPendingRequestResponse {

    /*
        "id":6,
        "mailer_address":"SANCHEZPIERO96@GMAIL.COM",
        "mailer_name":"SANCHEZ ARBILDO PIERO ALEJANDRO",
        "receiver_name":"ESPINOZA CARRANZA GABRIELA",
        "receiver_address":"GESPINOZACARRANZA@GMAIL.COM",
        "mailer_schedule":"OFF",
        "receiver_schedule":"09:00 - 19:00",
        "mailer_day_change":"22\/8\/2018",
        "status":1,
        "manager_name":"JEAN ROMANI",
        "manager_address":"jromani@newport.com.pe",
        "boss_name":"DANIELA GAMARRA",
        "boss_address":"ZAPATA093@HOTMAIL.COM",
     */


    private String mailer_address;   //local user's email
    private String mailer_name;          //local user's name
    private String receiver_address;       //the other user's email
    private String receiver_name;              //the other user's name
    private String mailer_schedule;        //local user's schedule
    private String receiver_schedule;       //person to change schedule schedule
    private String mailer_day_change;     //local user's day to ask to change
    private int status;                     //request status
    private String manager_name;            //local user's manager name
    private String manager_address;         //local user's manager email address
    private String boss_name;               //local user's boss name
    private String boss_address;            //local user's boss email address
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver_schedule() {
        return receiver_schedule;
    }

    public void setReceiver_schedule(String receiver_schedule) {
        this.receiver_schedule = receiver_schedule;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getManager_address() {
        return manager_address;
    }

    public void setManager_address(String manager_address) {
        this.manager_address = manager_address;
    }

    public String getBoss_name() {
        return boss_name;
    }

    public void setBoss_name(String boss_name) {
        this.boss_name = boss_name;
    }

    public String getBoss_address() {
        return boss_address;
    }

    public void setBoss_address(String boss_address) {
        this.boss_address = boss_address;
    }

    public String getMailer_address() {
        return mailer_address;
    }

    public void setMailer_address(String mailer_address) {
        this.mailer_address = mailer_address;
    }

    public String getMailer_name() {
        return mailer_name;
    }

    public void setMailer_name(String mailer_name) {
        this.mailer_name = mailer_name;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getMailer_schedule() {
        return mailer_schedule;
    }

    public void setMailer_schedule(String mailer_schedule) {
        this.mailer_schedule = mailer_schedule;
    }

    public String getMailer_day_change() {
        return mailer_day_change;
    }

    public void setMailer_day_change(String mailer_day_change) {
        this.mailer_day_change = mailer_day_change;
    }
}
