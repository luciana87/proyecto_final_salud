package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.JornadaLaboralRepositorio;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JornadaLaboralServicio {

    @Autowired
    private JornadaLaboralRepositorio jornadaLRepositorio;

    @Transactional
    public JornadaLaboral crearJornadaLaboral(Profesional profesional, String diaSemana, LocalTime horaInicio,
                                              LocalTime horaFin, Long duracionTurno) {

        JornadaLaboral jornadaLaboral = new JornadaLaboral();

        jornadaLaboral.setProfesional(profesional);
        jornadaLaboral.setDiaSemana(diaSemana);
        jornadaLaboral.setHoraInicio(horaInicio);
        jornadaLaboral.setHoraFin(horaFin);
        jornadaLaboral.setDuracionTurno(duracionTurno);

        jornadaLRepositorio.save(jornadaLaboral);

        return jornadaLaboral;
    }

    public List<JornadaLaboral> listarJornadas(Profesional profesional) throws MiException {

        List<JornadaLaboral> jornadasLaborales = new ArrayList();

        String id = profesional.getId();
        jornadasLaborales = jornadaLRepositorio.jornadasL(id);

        return jornadasLaborales;

    }

    public JornadaLaboral obtenerJornadaPorId(Integer id) {
        Optional<JornadaLaboral> jornadaOptional = jornadaLRepositorio.findById(id);
        return jornadaOptional.orElse(null);
    }

    @Transactional
    public void modificarJornada(Profesional profesional, Integer id_jornada, String diaSemana, LocalTime horaInicio,
            LocalTime horaFin, Long duracionTurno) throws MiException {

        Optional<JornadaLaboral> jornadasOptional = jornadaLRepositorio.findById(id_jornada);

        if (jornadasOptional.isPresent()) {
            JornadaLaboral jornada = jornadasOptional.get();

            jornada.setDiaSemana(diaSemana);
            jornada.setHoraInicio(horaInicio);
            jornada.setHoraFin(horaFin);
            jornada.setDuracionTurno(duracionTurno);

            jornadaLRepositorio.save(jornada);
        }
    }

    @Transactional
    public void eliminarJornada(Profesional profesional, Integer id_jornada) throws MiException{

        Optional<JornadaLaboral> jornada = jornadaLRepositorio.findById(id_jornada);
        System.out.println("entro2");
        if (jornada.isPresent()) {
            System.out.println("entro3");
            jornadaLRepositorio.deleteById(id_jornada);
        }
    }
    }
