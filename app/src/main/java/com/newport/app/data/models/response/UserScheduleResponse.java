package com.newport.app.data.models.response;

public class UserScheduleResponse {

    /**
     * horario":"00:00-09:00"
     * colaborador:"Juan Lucano"}]
     */

    private String horario;
    private String colaborador;

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }
}
