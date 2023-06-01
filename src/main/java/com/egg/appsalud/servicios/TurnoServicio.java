package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.transaction.Transactional;
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

    @Transactional
    public void crearTurno(Paciente paciente, Profesional profesional, LocalDate fechaTurno, String horario) throws MiException {
        Turno turno = new Turno();
        System.out.println("estoy en crear turno");
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setFecha(fechaTurno);
        turno.setHorario(horario);
        turno.setEstado(EstadoTurno.RESERVADO);
        turnoRepositorio.save(turno);
        List<Turno> listaDeTurnos= paciente.getListaDeTurnos();
        listaDeTurnos.add(turno);
        paciente.setListaDeTurnos(listaDeTurnos);
        pacienteRepositorio.save(paciente);
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
}
