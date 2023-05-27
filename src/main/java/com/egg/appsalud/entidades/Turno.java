package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumerativos.EstadoTurno;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "turno")
public class Turno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_turno;
   
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false, length = 50)
    private String horario;
    

    @ManyToOne //referencia a ‘Paciente’, muchos turnos puede tener un paciente.
    @JoinColumn(name = "paciente_id") //columna con la que vamos a relacionar esta tabla con la tabla ‘Paciente’.
    private Paciente paciente;

    @ManyToOne
    @JoinColumn (name= "profesional_id")
    private Profesional medico; 
    
    @Column(nullable = false)
    public EstadoTurno estado;

    public Turno(String id_turno, LocalDate fecha, Paciente paciente) {
        this.id_turno = id_turno;
        this.fecha = fecha;
        this.paciente = paciente;
        this.estado = EstadoTurno.RESERVADO;
    }

    public String getIdTurno() {
        return id_turno;
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


    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }
}
