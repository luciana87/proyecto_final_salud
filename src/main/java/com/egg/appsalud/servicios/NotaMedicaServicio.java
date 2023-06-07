/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.entidades.NotaMedica;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.HistoriaClinicaRepositorio;
import com.egg.appsalud.repositorios.NotaMedicaRepositorio;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author carel
 */
@Service
public class NotaMedicaServicio {

    @Autowired
    private NotaMedicaRepositorio notaMedicaRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Autowired
    TurnoRepositorio turnoRepositorio;

    @Transactional
    public void crearNotaMedica(String IdProfesional, String descripcion, Integer idTurno) throws MiException {

        Turno turno = turnoRepositorio.getOne(idTurno);

        NotaMedica notaMedica = new NotaMedica();

        HistoriaClinica hist = historiaClinicaRepositorio.buscarPaciente(turno.getPaciente());
        

//        NotaMedica notaM = null;
        Profesional profesional = profesionalRepositorio.getById(IdProfesional);

        if (hist == null) {

            historiaClinicaServicio.CrearHistoriaClinica(turno.getPaciente());
            
            HistoriaClinica historiaC = historiaClinicaRepositorio.buscarPaciente(turno.getPaciente());
            
            notaMedica.setHistoriaClinica(historiaC);
            notaMedica.setProfesional(profesional);
            notaMedica.setDescripcion(descripcion);
            turno.setEstado(EstadoTurno.ASISTIO);
            notaMedicaRepositorio.save(notaMedica);

            System.out.println("SE CREÓ UNA NUEVA HISTORIA");
        } else {
            notaMedica.setHistoriaClinica(hist);
            notaMedica.setProfesional(profesional);
            turno.setEstado(EstadoTurno.ASISTIO);
            notaMedica.setDescripcion(descripcion);
            notaMedicaRepositorio.save(notaMedica);
        }

    }
    @Transactional
    public void editarNotaMedica(Integer idNotaMedica, String descripcion){
        
        NotaMedica notaMedica = notaMedicaRepositorio.getOne(idNotaMedica);
        notaMedica.setDescripcion(descripcion);
        notaMedicaRepositorio.save(notaMedica);
    }
    
    @Transactional
    public void eliminarNotaMedica(Integer idNotaMedica){
        notaMedicaRepositorio.deleteById(idNotaMedica);
    }

    public List<NotaMedica>buscarPorHistoria(HistoriaClinica historiaClinica){
        return notaMedicaRepositorio.buscarPorHistoria(historiaClinica);

    }
}
