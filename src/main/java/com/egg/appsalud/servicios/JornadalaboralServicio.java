package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.JornadaLaboralRepositorio;
import java.sql.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JornadalaboralServicio {
    @Autowired
    private JornadaLaboralRepositorio jornadaLRepositorio;
    
    @Transactional
    public void crearJornadaLaboral(Profesional profesional, Integer diaSemana, Time horaInicio, Time horaFin, Integer duracionTurno){
        
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        
        jornadaLaboral.setProfesional(profesional);
        jornadaLaboral.setDiaSemana(diaSemana);
        jornadaLaboral.setHoraInicio(horaInicio);
        jornadaLaboral.setHoraFin(horaFin);
        jornadaLaboral.setDuracionTurno(duracionTurno);

        jornadaLRepositorio.save(jornadaLaboral);
    }
    
    
}
