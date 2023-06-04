package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Calificacion;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.repositorios.CalificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalificacionServicio {

    @Autowired
    ProfesionalServicio profesionalServicio;
    @Autowired
    CalificacionRepositorio calificacionRepositorio;

    @Autowired
    private TurnoServicio turnoServicio;


    @Transactional
    public void crearCalificacion(Integer turnoId, Integer puntaje) {

        Turno turno = turnoServicio.getOne(turnoId);
        if (turno != null) {

            Profesional profesional = turno.getMedico();
            if (profesional != null){

                Calificacion nuevaCalificacion = new Calificacion();
                nuevaCalificacion.setMedico(profesional);
                nuevaCalificacion.setPuntaje(puntaje);
                calificacionRepositorio.save(nuevaCalificacion);

                List<Calificacion> calificaciones = obtenerListaDeCalificaciones(profesional);
                calcularPromedio(calificaciones, profesional);
            }
        }
    }

    private List<Calificacion> obtenerListaDeCalificaciones(Profesional profesional) {
        List<Calificacion> calificaciones = calificacionRepositorio.calificacionesSegunProfesional(profesional);

        for (Calificacion calificacion: calificaciones) {
            System.out.println(calificacion.getPuntaje());
        }

        return calificaciones;
    }

    private void calcularPromedio(List<Calificacion> calificaciones, Profesional profesional){

        Double promedio;

        Double suma = 0D;
        for (Calificacion calificacion: calificaciones) {
            suma+= calificacion.getPuntaje();
        }

        promedio = Math.round((suma/calificaciones.size()) * 100.0) / 100.0;
        profesionalServicio.actualizarReputacion(promedio, profesional);
    }
}
