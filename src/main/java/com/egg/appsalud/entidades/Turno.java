package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turno")
public class Turno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_turno;
   
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime horario;
    

    @ManyToOne //referencia a ‘Paciente’, muchos turnos puede tener un paciente.
    @JoinColumn(name = "paciente_id") //columna con la que vamos a relacionar esta tabla con la tabla ‘Paciente’.
    private Paciente paciente;

    @ManyToOne
    @JoinColumn (name= "profesional_id")
    private Profesional medico; 
    
    @Column(nullable = false)
    public EstadoTurno estado;

    public Turno() {
    }
    
    

    public Turno( Integer id,LocalDate fecha, Paciente paciente) { 
        this.id_turno = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.estado = EstadoTurno.RESERVADO;
    }

    public Integer getIdTurno() {
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
    
    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Profesional getMedico() {
        return medico;
    }

    public void setMedico(Profesional medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Turno{" + "id_turno=" + id_turno + ", fecha=" + fecha + ", horario=" + horario + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + '}';
    }

    
    
}
