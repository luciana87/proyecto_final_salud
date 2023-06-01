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
<<<<<<< HEAD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
    @GeneratedValue(strategy = GenerationType.AUTO)
>>>>>>> devCarlos
    private Integer id;
   
    @Column(nullable = false)
    private LocalDate fecha;
    
<<<<<<< HEAD
    @Column(nullable = false)
    private LocalTime horario;
=======
    @Column(nullable = false, length = 50)
    private String horario;
>>>>>>> devCarlos
    

    @ManyToOne //referencia a ‘Paciente’, muchos turnos puede tener un paciente.
    @JoinColumn(name = "paciente_id") //columna con la que vamos a relacionar esta tabla con la tabla ‘Paciente’.
    private Paciente paciente;

    @ManyToOne
    @JoinColumn (name= "profesional_id")
<<<<<<< HEAD
    private Profesional medico; 
=======
    private Profesional profesional; 
>>>>>>> devCarlos
    
    @Column(nullable = false)
    public EstadoTurno estado;

    public Turno() {
<<<<<<< HEAD
    }
    
    

    public Turno( Integer id,LocalDate fecha, Paciente paciente) { 
        this.id = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.estado = EstadoTurno.RESERVADO;
    }

    public Integer getIdTurno() {
=======
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
>>>>>>> devCarlos
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

<<<<<<< HEAD
=======
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

>>>>>>> devCarlos
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

<<<<<<< HEAD
=======
    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
>>>>>>> devCarlos

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }
<<<<<<< HEAD
    
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
        return "Turno{" + "id_turno=" + id + ", fecha=" + fecha + ", horario=" + horario + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + '}';
    }


=======
>>>>>>> devCarlos

}
