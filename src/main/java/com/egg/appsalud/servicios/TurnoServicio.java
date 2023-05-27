package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Formato de fecha elegido

    public List<Turno> listarTurnosPorPacientes(Paciente paciente) {

        List<Turno> turnos = turnoRepositorio.BuscarPorPaciente(paciente);
        return turnos.stream().collect(Collectors.toList());
    }
}
