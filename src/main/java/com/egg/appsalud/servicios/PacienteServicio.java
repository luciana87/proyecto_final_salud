/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.Enumerativos.Rol;
import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 *
 * @author franc
 */
@Service
public class PacienteServicio implements UserDetailsService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ObraSocialServicio obraSocialServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Transactional
    public void CrearPaciente(MultipartFile archivo, String mail, String password, Integer idObraSocial,
            String nroObraSocial, String nombre, String apellido, String dni, LocalDate fechaNacimiento,
            String telefono) throws MiException, IOException {

        validar(mail, password, nombre, apellido, dni, fechaNacimiento, telefono, nroObraSocial);

        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setMail(mail);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setTelefono(telefono);
        paciente.setRol(Rol.PACIENTE);

        if (idObraSocial != null) {
            ObraSocial obraSocial = obraSocialServicio.getOne(idObraSocial);
            paciente.setObraSocial(obraSocial);
            paciente.setNroObraSocial(nroObraSocial);
        }
        if (!archivo.isEmpty()) {  // Si cargó una imágen, se la setteo al paciente.
            Imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);
        }
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

    private void validar(String mail, String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento, String telefono, String nroObraSocial) throws MiException {
        if (nombre.isEmpty() || !ComprobarString(nombre, "^[a-zA-Z]+$")) {
            throw new MiException("Error en el formato de nombre, o es nulo");
        }

        if (password.isEmpty() || !ComprobarString(password, "^(?=.*[A-Z]).{8,}$")) {
            throw new MiException("La debe ser de al menos 8 caracteres y contener una mayuscula");
        }
        if (mail.isEmpty() || !ComprobarString(mail, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new MiException("Ingrese un Email valido");
        }
        if (apellido.isEmpty() || !ComprobarString(apellido, "^[a-zA-ZñÑ]+$")) {
            throw new MiException("Error en el formato del apellido, o es nulo");
        }
        if (dni.isEmpty() || !ComprobarString(dni, "^\\d{8}$")) {
            throw new MiException("Ingrese un dni valido");
        }
        //crear la logica para validar que sea mayor de edad
        if (fechaNacimiento == null) {
            throw new MiException("La fecha de naciemiento no puede ser nulo o estar vacio");
        }
        if (telefono.isEmpty() || !ComprobarString(telefono, "^(11|0|15)\\d{8}$")) {
            throw new MiException("Debe inicar un telefono valido");
        }
        if (nroObraSocial.isEmpty() || nroObraSocial == null)// hay que validar bien al nro de ObraSocial
        {
            throw new MiException("Ingrese una Obra Social");
        }
        //validar tambien el id de obra social que el ususario selecione una
    }

    private void validarModificar(String mail, String nombre, String apellido, String dni, LocalDate fechaNacimiento, String telefono, String nroObraSocial) throws MiException {
        if (nombre.isEmpty() || !ComprobarString(nombre, "^[a-zA-Z]+$")) {
            throw new MiException("Error en el formato de nombre, o es nulo");
        }

        if (mail.isEmpty() || !ComprobarString(mail, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new MiException("Ingrese un Email valido");
        }
        if (apellido.isEmpty() || !ComprobarString(apellido, "^[a-zA-ZñÑ]+$")) {
            throw new MiException("Error en el formato del apellido, o es nulo");
        }
        if (dni.isEmpty() || !ComprobarString(dni, "^\\d{8}$")) {
            throw new MiException("Ingrese un dni valido");
        }
        //crear la logica para validar que sea mayor de edad
        if (fechaNacimiento == null) {
            throw new MiException("La fecha de naciemiento no puede ser nulo o estar vacio");
        }
        if (telefono.isEmpty() || !ComprobarString(telefono, "^[0-9]+$")) {
            throw new MiException("Debe inicar un telefono valido");
        }
        if (nroObraSocial.isEmpty() || nroObraSocial == null)// hay que validar bien al nro de ObraSocial
        {
            throw new MiException("Ingrese una Obra Social");
        }
        //validar tambien el id de obra social que el ususario selecione una
    }

    //regex
    final boolean ComprobarString(String cadena, String regex) {
        return cadena.matches(regex);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Paciente paciente = pacienteRepositorio.BuscarPorEmail(mail);

        if (paciente != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + paciente.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("pacientesession", paciente);

            return new User(paciente.getMail(), paciente.getPassword(), permisos);

        } else {
            return null;
        }

    }

    @Transactional
    public void modificarPaciente(MultipartFile archivo, String id_paciente, String mail, String nombre,
            String apellido, String dni, LocalDate fechaNacimiento, String telefono, String nroObraSocial, Integer idObraSocial) throws MiException, IOException {

        validarModificar(mail, nombre, apellido, dni, fechaNacimiento, telefono, nroObraSocial);
       
        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id_paciente);
        
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            ObraSocial obraSocial = obraSocialServicio.getOne(idObraSocial);
                
                
            
            paciente.setMail(mail);
            paciente.setObraSocial(obraSocial);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setTelefono(telefono);
            
            String idImagen = null;
            if (paciente.getImagen() != null) {
                idImagen = paciente.getImagen().getId();
            }

            if (!archivo.isEmpty()) {  // Si cargó una imágen, se la setteo al paciente.
                Imagen imagen = imagenServicio.guardar(archivo);
                paciente.setImagen(imagen);
            }
            pacienteRepositorio.save(paciente);

        }
    }

    public Paciente getOne(String id_paciente) {
        return pacienteRepositorio.getOne(id_paciente);
    }

    @Transactional
    public void eliminarPaciente(String id_paciente) throws MiException {
        pacienteRepositorio.deleteById(id_paciente);

    }

    // FILTRO DE BUSQUEDA
    
    // Buscar profesionales
//    public List<Profesional> buscarProfesionales(Especialidad especialidad, Double valorConsulta, Double reputacion) {
//
//        List<Profesional> profesionales = profesionalServicio.buscarPorfesional(especialidad, valorConsulta, reputacion);
//
//        return profesionales;
//
//    }
//    
//    // Uso el id del profesional seleccionado para buscarlo en la DB 
//    public Profesional seleccionarProfesional(String id_profesional){
//        Profesional pro = profesionalServicio.seleccionarProfesional(id_profesional);
//        
//        return pro;
//    }

    @Transactional
    public void cambiarContrasenia(String idPaciente, String contraVieja, String contraNueva, String contraComparar) throws MiException{
        
             Paciente paciente = pacienteRepositorio.getOne(idPaciente);
             validarContraseña(contraVieja, paciente.getPassword(), contraNueva, contraComparar);
             
             paciente.setPassword(new BCryptPasswordEncoder().encode(contraNueva));
             
             pacienteRepositorio.save(paciente);
           
    }
    
    private void validarContraseña(String contraVieja,String contraBS, String contraNueva, String contraComparar) throws MiException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(contraVieja, contraBS)){
            throw new MiException("Ingrese devuelta su contraseña");
        }
        if(!contraNueva.equals(contraComparar)){
            throw new MiException("No coiciden las nuevas contraseñas");
        }
    }
}

