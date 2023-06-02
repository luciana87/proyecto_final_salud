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
<<<<<<< HEAD
=======


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
    public void CambiarTurnoReservado(Integer idTurno){
        Turno turno = turnoRepositorio.getOne(idTurno);
        turno.setEstado(EstadoTurno.RESERVADO);
        turnoRepositorio.save(turno);
    }
    
    
    public List<Turno>ListarTurnoProfesional(Profesional profesional){
        
        return turnoRepositorio.BuscarTurnosProfecional(profesional);
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
>>>>>>> develop
        

    }

}
