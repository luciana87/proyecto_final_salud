/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;

import java.awt.*;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author franc
 */
@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;

    @Transactional
    public void CrearPaciente(MultipartFile archivo, String mail, String password, Integer idObraSocial,
                              String nroObraSocial, String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                              Long telefono) throws MiException {

        validar(mail, password, nombre, apellido, dni, fechaNacimiento);

        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setMail(mail);
        paciente.setPassword(password);
        paciente.setTelefono(telefono);

        if (idObraSocial != null){
            ObraSocial obraSocial = obraSocialServicio.getOne(idObraSocial);
            paciente.setObraSocial(obraSocial);
            paciente.setNroObraSocial(nroObraSocial);
        }

        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);

        pacienteRepositorio.save(paciente);

    }

    public List<Paciente> listarPacientes() {

        List<Paciente> pacientes = pacienteRepositorio.findAll();
        return pacientes.stream().collect(Collectors.toList());
    }

    public Paciente buscarPorId(String idPaciente) throws MiException {
        Optional<Paciente> paciente = pacienteRepositorio.findById(idPaciente);
        if (!paciente.isPresent()) {
            throw new MiException("Paciente no encontrado.");
        }
        return paciente.get();
    }

    @Transactional
    public void modificarPaciente(MultipartFile archivo, String id_paciente, String mail, String password, String nombre,
            String apellido, String dni, LocalDate fechaNacimiento, Long telefono) throws MiException {

        validar(mail, password, nombre, apellido, dni, fechaNacimiento);

        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id_paciente);

        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();

            paciente.setMail(mail);
            paciente.setPassword(password);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setTelefono(telefono);

            String idImagen = null;
            if (paciente.getImagen() != null){
                idImagen = paciente.getImagen().getId();
            }
            Imagen imagen = imagenServicio.actualizarImagen(archivo, idImagen);
            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);

        }
    }

    public Paciente getOne(String id_paciente) {
        return pacienteRepositorio.getOne(id_paciente);
    }

    private void validar(String mail, String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nulo o estar vacia");
        }
        if (mail.isEmpty() || mail == null) {
            throw new MiException("El correo no puede ser nulo o estar vacio");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiException("El DNI no puede ser nulo o estar vacio");
        }
        if (fechaNacimiento == null) {
            throw new MiException("La fecha de naciemiento no puede ser nulo o estar vacia");
        }
    
    }

}