package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;

import com.egg.appsalud.repositorios.TurnoRepositorio;
import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    // private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Formato de fecha elegido /*
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private PacienteServicio pacienteServicio;

    public List<Turno> obtenerTodosLosTurnos() {
        return turnoRepositorio.findAll();
    }

    public Optional<Turno> obtenerTurnoPorId(int id) {
        return turnoRepositorio.findById(id);
    }

    
    public void sacarTurno(int id, Paciente paciente, Profesional profesional, LocalDate fecha) {

        Optional<Turno> turnoOptional = turnoRepositorio.findById(id);
        if (turnoOptional.isPresent()) {
            Turno turno = turnoOptional.get();
            turno.setPaciente(paciente);
            turno.setProfesional(profesional);
            turno.setEstado(true);
            turno.setFecha(fecha);
            turnoRepositorio.save(turno);
        }

    }

    

    
    public void editarTurno (int id, Paciente paciente, Profesional profesional, LocalDate fecha){
        
                 Optional<Turno> turnoOptional = turnoRepositorio.findById(id);
	        Optional<Paciente> pacienteOptional = pacienteServicio.obtenerPacientePorId(paciente.getId());
	        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(profesional.getId());
	        if (turnoOptional.isPresent() && pacienteOptional.isPresent() && profesionalOptional.isPresent()) {
	            Turno turno = turnoOptional.get();
                    turno.setPaciente(paciente);
                    turno.setProfesional(profesional);
                    turno.setFecha(fecha);
	            turno.setEstado(true);
                    turnoRepositorio.save(turno);
   
    }
    }
    
    public Turno guardarTurno(Turno turno) {
        // Verificar si hay otro turno en el mismo horario
        LocalDate fecha = turno.getFecha();
        Optional<Turno> turnoExistente = turnoRepositorio.findByFecha(fecha);

        if (turnoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un turno en el mismo horario.");
        }

        // Verificar la duración del turno
        LocalDateTime inicio = turno.getFechaHoraInicio();
        LocalDateTime fin = turno.getFechaHoraFin();
        Duration duracion = Duration.between(inicio, fin);

        if (duracion.toMinutes() > 30) {
            throw new IllegalArgumentException("La duración del turno no puede superar los 30 minutos.");
        }

        return turnoRepositorio.save(turno);
    }
    
    
    
    
}


