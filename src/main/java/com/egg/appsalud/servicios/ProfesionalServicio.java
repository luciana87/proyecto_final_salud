package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.Enumerativos.Rol;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Transactional
    public void crearProfesional(String mail, String password, String nombre, String apellido,
                                 String dni, LocalDate fechaNacimiento, Long telefono, String matricula,
                                 String especialidad, Double valorConsulta, String descripcionEspecialidad) throws MiException {


            validar(mail, password, nombre, apellido, dni, fechaNacimiento);

            Profesional profesional = new Profesional();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setDni(dni);
            profesional.setFechaNacimiento(fechaNacimiento);
            profesional.setMail(mail);
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setTelefono(telefono);
            profesional.setMatricula(matricula);
            profesional.setEspecialidad(especialidad);
            profesional.setValorConsulta(valorConsulta);
            profesional.setDescripcionEspecialidad(descripcionEspecialidad);
            profesional.setRol(Rol.PROFESIONAL);

//            En caso de tener foto de perfil:
//            Imagen imagen = imagenServicio.guardar(archivo);
//            profesional.setImagen(imagen);

            profesionalRepositorio.save(profesional);
    }


    @Transactional
    public void modificarProfesional(String idProfesional, String mail, String password, String nombre, String apellido,
                                     String dni, LocalDate fechaNacimiento, Long telefono, String matricula, String especialidad,
                                     Double valorConsulta, String descripcionEspecialidad) throws MiException {

        validar(mail, password, nombre, apellido, dni, fechaNacimiento);

        Optional<Profesional> profesionalOptional = profesionalRepositorio.findById(idProfesional);

        if (profesionalOptional.isPresent()) {
            Profesional profesional = profesionalOptional.get();

            profesional.setMail(mail);
            profesional.setPassword(password);
            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setDni(dni);
            profesional.setFechaNacimiento(fechaNacimiento);
            profesional.setTelefono(telefono);
            profesional.setMatricula(matricula);
            profesional.setEspecialidad(especialidad);
            profesional.setValorConsulta(valorConsulta);
            profesional.setDescripcionEspecialidad(descripcionEspecialidad);

            profesionalRepositorio.save(profesional);

        }
    }

    public Profesional getOne(String idProfesional){
        return profesionalRepositorio.getOne(idProfesional);
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
