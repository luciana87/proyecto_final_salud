package com.egg.appsalud.entidades;



import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "turno")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private LocalDate fecha;
  

    @ManyToOne 
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;


    @ManyToOne
    @JoinColumn (name= "profesional_id")
    private Profesional profesional; 
    
    @Column(nullable = false)
    public boolean estado;

    public Turno() {
    }

    public Turno(int id, LocalDate fecha, Paciente paciente, Profesional profesional, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.profesional = profesional;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
  
}
