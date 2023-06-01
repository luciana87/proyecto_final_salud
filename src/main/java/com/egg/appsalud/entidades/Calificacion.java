package com.egg.appsalud.entidades;

import javax.persistence.*;

@Entity
@Table (name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer puntaje;
    @ManyToOne
    @JoinColumn (name= "profesional_id", nullable = false)
    private Profesional medico;

    public Calificacion( Integer puntaje, Profesional medico) {
        this.puntaje = puntaje;
        this.medico = medico;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Profesional getMedico() {
        return medico;
    }

    public void setMedico(Profesional medico) {
        this.medico = medico;
    }
}
