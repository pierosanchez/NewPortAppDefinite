package com.newport.app.data.models.request;

public class SwitchScheduleEmailRequest {

    private int id;
    private String mailerAddress;   //local user's email
    private String mailer;          //local user's name
    private String toAddress;       //the other user's email
    private String to;              //the other user's name
    private String schedule;        //local user's schedule
    private String dayToChange;     //local user's day to ask to change
    private String bossAddress;     //local user's boss email address
    private String bossName;        //local user's boss name
    private String managerAddress;  //local user's manager email address
    private String managerName;     //local user's manager name
    private String scheduleSecondUser;      //the other user's schedule
    private int status;             //switch schedule request status
    private String area;            //local user's area where he or she works
    private String sala;            //local user's Casino where he or she works

    /*
        status could be:
        1 = pendiente de aprovaciones
        2 = aprobado el segundo usuario
        3 = aprobado por el jefe de sala
        4 = aprobado por el gerente de sala
        5 = rechazado por el segundo usuario
        6 = rechazado por el jefe
        7 = rechasado por el gerente
     */

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBossAddress() {
        return bossAddress;
    }

    public void setBossAddress(String bossAddress) {
        this.bossAddress = bossAddress;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getScheduleSecondUser() {
        return scheduleSecondUser;
    }

    public void setScheduleSecondUser(String scheduleSecondUser) {
        this.scheduleSecondUser = scheduleSecondUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMailerAddress() {
        return mailerAddress;
    }

    public void setMailerAddress(String mailerAddress) {
        this.mailerAddress = mailerAddress;
    }

    public String getMailer() {
        return mailer;
    }

    public void setMailer(String mailer) {
        this.mailer = mailer;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDayToChange() {
        return dayToChange;
    }

    public void setDayToChange(String dayToChange) {
        this.dayToChange = dayToChange;
    }
}
