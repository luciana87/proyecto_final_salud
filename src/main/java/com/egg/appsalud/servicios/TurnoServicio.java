package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import java.sql.Time;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Formato de fecha elegido

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    public Turno getOne(String id) {
        return turnoRepositorio.getOne(id);
    }

    public List<Turno> buscarTurnos() {
        List<Turno> turnos = new ArrayList();

        turnos = turnoRepositorio.findAll();

        return turnos;
    }

    @Transactional
    public void crearTurno(String id_paciente, String id_profesional, LocalDate fecha, String horario) throws MiException {

        Optional<Paciente> respPaciente = pacienteRepositorio.findById(id_paciente);

        Optional<Profesional> respProfesional = profesionalRepositorio.findById(id_profesional);

        if (respPaciente.isPresent() && respProfesional.isPresent()) {
            Paciente paciente = respPaciente.get();
            Profesional profesional = respProfesional.get();

//            Turno turno = new Turno(null, fecha, paciente, profesional);
//            turnoRepositorio.save(turno);
        }

    }

//    public Profesional obtenerProfesionalPorId(String id_profesional) {
//        return profesionalRepositorio.getById(id_profesional);
//    }

    public void validar() throws MiException {
        

    }

}
