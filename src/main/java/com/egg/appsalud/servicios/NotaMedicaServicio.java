/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.entidades.NotaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.NotaMedicaRepositorio;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    
    @Transactional
    public void crearNotaMedica(String IdProfesional, String descripcion, String IdPaciente) throws MiException {
        NotaMedica notaMedica = new NotaMedica();
        Paciente paciente = new Paciente();
        paciente = pacienteRepositorio.getOne(IdPaciente);
        List<NotaMedica> listas=null;
        Profesional profesional = profesionalRepositorio.getById(IdProfesional);
        if (paciente.getHistoriaClinica() == null) {
            HistoriaClinica historiaClinica = historiaClinicaServicio.CrearHistoriaClinica(paciente, listas);
            notaMedica.setHistoriaClinica(historiaClinica);
            paciente.setHistoriaClinica(historiaClinica);
            pacienteRepositorio.save(paciente);
            System.out.println("DEBERIA CREAR NUEVA HISTORIA");
        }else{
          notaMedica.setHistoriaClinica(paciente.getHistoriaClinica());  
        }
        notaMedica.setProfesional(profesional);
        notaMedica.setDescripcion(descripcion);
        notaMedicaRepositorio.save(notaMedica);
        
        
  
    }
}
