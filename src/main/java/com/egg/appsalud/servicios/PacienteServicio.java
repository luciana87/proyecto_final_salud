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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *
 * @author franc
 */
@Service
public class PacienteServicio {
    
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    @Transactional
    public void CrearPaciente(String mail,String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento) throws MiException{
        
        validar(mail, password, nombre, apellido, dni, fechaNacimiento);
        
        Paciente paciente = new Paciente();
        
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setMail(mail);
        paciente.setPassword(password);
        
        pacienteRepositorio.save(paciente);
        
    }

    public List<Paciente> listarPacientes() {

        List<Paciente> pacientes = pacienteRepositorio.findAll();
        return pacientes.stream().collect(Collectors.toList());
    }

    public Paciente buscarPorId(String idPaciente) throws MiException {
        Optional<Paciente> paciente = pacienteRepositorio.findById(idPaciente);
        if (!paciente.isPresent()){
            throw new MiException("Paciente no encontrado.");
        }
        return paciente.get();
    }
    
    private void validar(String mail,String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento) throws MiException{
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        
        if(password.isEmpty() || password == null){
            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }
        if(mail.isEmpty() || mail == null){
            throw new MiException("El correo no puede ser nulo o estar vacio");
        }
        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }
        if(dni.isEmpty() || dni == null){
            throw new MiException("El DNI no puede ser nulo o estar vacio");
        }
        if(fechaNacimiento == null){
            throw new MiException("La fecha de naciemiento no puede ser nulo o estar vacio");
        }
    }


}