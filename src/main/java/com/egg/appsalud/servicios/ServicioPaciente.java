/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
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
    public void CrearPaciente(String mail,String password, String nombre, String apellido, String dni, int edad, long telefono) throws MiException{
        
        validar(mail, password, nombre, apellido, dni, edad);
        
        Paciente paciente = new Paciente();
        
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setEdad(edad);
        paciente.setMail(mail);
        paciente.setPassword(password);
        paciente.setTelefono(telefono);
        
        pacienteRepo.save(paciente);
        
    }
    
    private void validar(String mail,String password, String nombre, String apellido, String dni, Integer edad) throws MiException{
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        
        if(password.isEmpty() || password == null){
            throw new MiException("El cuerpo no puede ser nulo o estar vacio");
        }
        if(mail.isEmpty() || mail == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if(dni.isEmpty() || dni == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if(edad < 0 || edad == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
    }
}
