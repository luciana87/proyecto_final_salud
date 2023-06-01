package com.egg.appsalud.entidades;

import java.sql.Time;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class JornadaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_profesional")
    private Profesional profesional;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long duracionTurno;

    public JornadaLaboral() {
    }

    public JornadaLaboral(Profesional profesional, String diaSemana,
                          LocalTime horaInicio, LocalTime horaFin, Long duracionTurno) {

        this.profesional = profesional;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.duracionTurno = duracionTurno;
    }


    public Integer getId() {
        return id;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getDuracionTurno() {
        return duracionTurno;
    }

    public void setDuracionTurno(Long duracionTurno) {
        this.duracionTurno = duracionTurno;
    }
}
