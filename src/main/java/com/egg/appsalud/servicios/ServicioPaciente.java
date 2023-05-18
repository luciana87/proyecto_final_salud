/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author franc
 */
@Service
public class ServicioPaciente {
    
    @Autowired
    private PacienteRepositorio pacienteRepo;
    
    @Transactional
    public void CrearPaciente(String mail,String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento, long telefono) throws MiException{
        
        validar(mail, password, nombre, apellido, dni, fechaNacimiento);
        
        Paciente paciente = new Paciente();
        
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setMail(mail);
        paciente.setPassword(password);
        paciente.setTelefono(telefono);
        
        pacienteRepo.save(paciente);
        
    }
    
    private void validar(String mail,String password, String nombre, String apellido, String dni, LocalDate edad) throws MiException{
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El Nombre no puede ser nulo o estar vacio");
        }
        
        if(password.isEmpty() || password == null){
            throw new MiException("El cuerpo no puede ser nulo o estar vacio");
        }
        if(mail.isEmpty() || mail == null){
            throw new MiException("El Mail no puede ser nulo o estar vacio");
        }
        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El Apellido no puede ser nulo o estar vacio");
        }
        if(dni.isEmpty() || dni == null){
            throw new MiException("El Dni no puede ser nulo o estar vacio");
        }
        if( edad == null){
            throw new MiException("La edad no puede ser nulo o estar vacio");
        }
    }
}
