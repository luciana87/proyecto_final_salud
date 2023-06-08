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

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.EspecialidadRepositorio;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
//import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

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
                                    while (tiempo.isBefore(jornada.getHoraFin()) || tiempo.equals(jornada.getHoraFin()) ) {
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
    
    @Transactional
    public void SelecionarTurnoPaciente(String pacieteId, Integer idTurno){
        Paciente paciente = pacienteRepositorio.getOne(pacieteId);
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.RESERVADO);
        turno.setPaciente(paciente);
        turnoRepositorio.save(turno);
    }


    //fran
    public List<Turno> listarTurnosPaciente(String pacieteId){
        Paciente paciente = pacienteRepositorio.getOne(pacieteId);
        return turnoRepositorio.BuscarTurnosPaciente(paciente);
    }

    public List<Turno>ListarTurnoProfesional(String profesionalId){
        Profesional profesional = profesionalRepositorio.getReferenceById(profesionalId);
        return turnoRepositorio.BuscarTurnosProfecional(profesional);
    }

    public List<Turno>ListarTurnoProfesional(Profesional profesional){
        return turnoRepositorio.BuscarTurnosProfecional(profesional);
    }
//tira error en el repo
    
//    public List<Turno> listarTurnosPorPacientes(Paciente paciente){
//        List<Turno> turnos = turnoRepositorio.BuscarPorPaciente(paciente);
//        return turnos.stream().collect(Collectors.toList());
//    }


    @Transactional
    public void CambiarTurnoAsistio(Integer idTurno){
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.ASISTIO);
        turnoRepositorio.save(turno);
    }
    
    @Transactional
    public void CambiarTurnoCancelado(Integer idTurno){
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.CANCELADO);
        turnoRepositorio.save(turno);
    }
    
    @Transactional
    public void CambiarTurnoDisponible(Integer idTurno){
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.DISPONIBLE);
        turnoRepositorio.save(turno);
    }
    @Transactional
    public void CambiarTurnoReservado(Integer idTurno, Paciente paciente){
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.RESERVADO);
        turno.setPaciente(paciente);
        turnoRepositorio.save(turno);
    }
    
    private List<LocalDate> listarFechasSegunRango (LocalDate inicioRango, LocalDate finRango){
        List<LocalDate> dates = Stream.iterate(inicioRango, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(inicioRango, finRango))
                .collect(Collectors.toList());

        return dates;
    }
    
    public List<Turno> listarTurnos() {
        List<Turno> turnos = turnoRepositorio.findAll();
        System.out.println("Turnos "+turnos.get(0).getPaciente().getNombre());
     return turnos.stream().collect(Collectors.toList());
    }    
    
    public Turno listarTurnosporId(Integer idTurno) {
        Optional<Turno> condicion = turnoRepositorio.findById(idTurno);
        Turno turno = new Turno();
        if (condicion.isPresent()) {
            
           turno = condicion.get();
           
        }
        
        return turno;
    }
    public Turno buscarPorId(Integer id) {
        return turnoRepositorio.findById(id).orElse(null);
    }

    public List<Turno>buscarTurnosFiltro( String idProfesional, LocalDate fecha, LocalTime horario,  String nombre,  Double valorConsulta, EstadoTurno estado,Double reputacion,Integer especialidad){
        Profesional medico = null;
        Especialidad espe = null;
        Optional<Profesional> medicoOptional = profesionalRepositorio.findById(idProfesional);
        if(especialidad != null){
            espe = especialidadRepositorio.getOne(especialidad);
        }
        

        if (medicoOptional.isPresent()){
            medico = medicoOptional.get();
        }

        List<Turno>listaDeTurnos = turnoRepositorio.buscarTurnosFiltro(medico, fecha, horario, nombre, valorConsulta,estado,reputacion,espe);
        return listaDeTurnos;
    }

    @Transactional
    public void eliminarTurno(Integer id) throws MiException {
        turnoRepositorio.deleteById(id);
    }

    @Transactional
    public void BorraTurno(Integer id){
        Turno turno = turnoRepositorio.getOne(id);
        turnoRepositorio.delete(turno);
    }
}
