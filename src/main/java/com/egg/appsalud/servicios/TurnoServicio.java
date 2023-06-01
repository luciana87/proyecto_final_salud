package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.*;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Turno getOne(Integer id) {
        return turnoRepositorio.getOne(id);
    }

    public List<Turno> buscarTurnos() {
        List<Turno> turnos = new ArrayList();

        turnos = turnoRepositorio.findAll();

        return turnos;
    }

    @Transactional
    public void crearTurno(String id, LocalDate inicioRango, LocalDate finRango) throws MiException {

        Optional<Profesional> respProfesional = profesionalRepositorio.findById(id);


        if (respProfesional.isPresent()) {
            Profesional profesional = respProfesional.get();

            if (profesional.getJornadaLaboral() != null){
                List<JornadaLaboral> jornadaLaboral = profesional.getJornadaLaboral();

                List<LocalDate> fechas = listarFechasSegunRango(inicioRango, finRango);
                List<Turno> turnos = new ArrayList<>();

                for (LocalDate fecha : fechas) {
                        for (JornadaLaboral jornada : jornadaLaboral)
                                if (fecha.getDayOfWeek().toString().equals(jornada.getDiaSemana())){
                                    LocalTime tiempo = jornada.getHoraInicio();
                                    while (tiempo.isBefore(jornada.getHoraFin())) {
                                            Turno turno = new Turno();
                                            turno.setEstado(EstadoTurno.DISPONIBLE);
                                            turno.setFecha(fecha);
                                            turno.setHorario(tiempo);
                                            turno.setMedico(profesional);
                                            turnos.add(turno);
//                                            turnoRepositorio.save(turno);
                                            tiempo = tiempo.plusMinutes(jornada.getDuracionTurno());

                                        }
                                }
                }
                    turnoRepositorio.saveAll(turnos);
                }
            }
        }


//    public Profesional obtenerProfesionalPorId(String id_profesional) {
//        return profesionalRepositorio.getById(id_profesional);
//    }

    public void validar() throws MiException {


    }

//    public List<Turno> listarTurnosPorPacientes(Paciente paciente) {
//
//        List<Turno> turnos = turnoRepositorio.BuscarPorPaciente(paciente);
//        return turnos.stream().collect(Collectors.toList());
//    }


    private List<LocalDate> listarFechasSegunRango (LocalDate inicioRango, LocalDate finRango){
        List<LocalDate> dates = Stream.iterate(inicioRango, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(inicioRango, finRango))
                .collect(Collectors.toList());

        return dates;
    }

}
