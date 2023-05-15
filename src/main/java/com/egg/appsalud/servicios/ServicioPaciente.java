/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public void CrearPaciente(String mail, String password, String nombre, String apellido, String dni, Integer edad) throws MiException {

        validar(mail, password, nombre, apellido, dni, edad);

        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setEdad(edad);
        paciente.setMail(mail);
        paciente.setPassword(password);
        
        pacienteRepo.save(paciente);

    }

    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList();

        pacientes = pacienteRepo.findAll();

        return pacientes;
    }

    @Transactional
    public void modificarPaciente(String idPaciente, String mail, String password, String nombre,
            String apellido, String dni, Integer edad, Long telefono) throws MiException {
        
        validar(mail, password, nombre, apellido, dni, edad);
        
        Optional<Paciente> pacienteOptional = pacienteRepo.findById(idPaciente);

        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            
            paciente.setMail(mail);
            paciente.setPassword(password);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setEdad(edad);
            paciente.setTelefono(telefono);

            pacienteRepo.save(paciente);
            
        } else {
            throw new MiException("No se encontró el paciente con el ID: " + idPaciente);
        }
    }

    public Paciente getOne(String id_paciente) {
        return pacienteRepo.getOne(id_paciente);
    }
    private void validar(String mail, String password, String nombre, String apellido, String dni, Integer edad) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null) {
            throw new MiException("El cuerpo no puede ser nulo o estar vacio");
        }
        if (mail.isEmpty() || mail == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (edad < 0 || edad == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
    }
}
