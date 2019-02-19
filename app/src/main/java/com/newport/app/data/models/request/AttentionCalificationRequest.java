package com.newport.app.data.models.request;

public class AttentionCalificationRequest {
    private int chat_id;
    private int calification;
    private int calification_solution_problem;

    public int getCalification_solution_problem() {
        return calification_solution_problem;
    }

    public void setCalification_solution_problem(int calification_solution_problem) {
        this.calification_solution_problem = calification_solution_problem;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getCalification() {
        return calification;
    }

    public void setCalification(int calification) {
        this.calification = calification;
    }
}
