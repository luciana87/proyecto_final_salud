package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumerativos.EstadoTurno;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "turno")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
   
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false, length = 50)
    private String horario;
    

    @ManyToOne //referencia a ‘Paciente’, muchos turnos puede tener un paciente.
    @JoinColumn(name = "paciente_id") //columna con la que vamos a relacionar esta tabla con la tabla ‘Paciente’.
    private Paciente paciente;

    @ManyToOne
    @JoinColumn (name= "profesional_id")
    private Profesional profesional; 
    
    @Column(nullable = false)
    public EstadoTurno estado;

    public Turno() {
    }

    public Turno(Integer id, LocalDate fecha, String horario, Paciente paciente, Profesional profesional, EstadoTurno estado) {
        this.id = id;
        this.fecha = fecha;
        this.horario = horario;
        this.paciente = paciente;
        this.profesional = profesional;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

}
