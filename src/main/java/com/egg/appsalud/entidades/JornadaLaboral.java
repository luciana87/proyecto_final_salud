package com.egg.appsalud.entidades;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class JornadaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_jornada;
    
    @ManyToOne
    @JoinColumn(name = "id_profesional")
    private Profesional profesional;
    
    private Integer diaSemana;
    private Time horaInicio;
    private Time horaFin;
    private Integer duracionTurno;

    public JornadaLaboral() {
    }

    public JornadaLaboral(String id_jornada, Profesional profesional, Integer diaSemana, Time horaInicio, Time horaFin, Integer duracionTurno) {
        this.id_jornada = id_jornada;
        this.profesional = profesional;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.duracionTurno = duracionTurno;
    }

    public String getId_jornada() {
        return id_jornada;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public Integer getDuracionTurno() {
        return duracionTurno;
    }

    public void setId_jornada(String id_jornada) {
        this.id_jornada = id_jornada;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public void setDuracionTurno(Integer duracionTurno) {
        this.duracionTurno = duracionTurno;
    }
    
    
}
