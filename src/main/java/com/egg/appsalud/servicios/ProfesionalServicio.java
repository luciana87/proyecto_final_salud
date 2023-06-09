package com.egg.appsalud.servicios;


import com.egg.appsalud.Enumerativos.Rol;
import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.entidades.JornadaLaboral;
//import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.EspecialidadRepositorio;
import com.egg.appsalud.repositorios.JornadaLaboralRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private JornadaLaboralServicio jornadaServicio;
    @Autowired
    private JornadaLaboralRepositorio jornadaLaboralRepositorio;
    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    @Transactional
    public void crearProfesional(String mail, String password, String nombre, String apellido,
            String dni, LocalDate fechaNacimiento, String telefono, String matricula,
            Integer especialidad, Double valorConsulta, String descripcionEspecialidad) throws MiException {

        validar(mail, password, nombre, apellido, dni, fechaNacimiento);

        Profesional profesional = new Profesional();
        Especialidad espec = especialidadRepositorio.getOne(especialidad);

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setDni(dni);
        profesional.setFechaNacimiento(fechaNacimiento);
        profesional.setMail(mail);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setTelefono(telefono);
        profesional.setMatricula(matricula);
        profesional.setEspecialidad(espec);
        profesional.setValorConsulta(valorConsulta);
        profesional.setDescripcionEspecialidad(descripcionEspecialidad);
        profesional.setRol(Rol.PROFESIONAL);

//            En caso de tener foto de perfil:
//            Imagen imagen = imagenServicio.guardar(archivo);
//            profesional.setImagen(imagen);
        profesionalRepositorio.save(profesional);
    }

    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = profesionalRepositorio.findAll();
        return profesionales.stream().collect(Collectors.toList());
    }

    @Transactional
    public void modificarProfesional(String idProfesional, String mail, String nombre, String apellido,
            String dni, LocalDate fechaNacimiento, String telefono, String matricula, Integer especialidad,
            Double valorConsulta, String descripcionEspecialidad) throws MiException {

//        validarModificar(mail, nombre, apellido, dni, telefono ,fechaNacimiento);
        Optional<Profesional> profesionalOptional = profesionalRepositorio.findById(idProfesional);

        if (profesionalOptional.isPresent()) {
            Profesional profesional = profesionalOptional.get();
            Especialidad espec = especialidadRepositorio.getOne(especialidad);

            profesional.setMail(mail);
            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setDni(dni);
            profesional.setFechaNacimiento(fechaNacimiento);
            profesional.setTelefono(telefono);
            profesional.setMatricula(matricula);
            profesional.setEspecialidad(espec);
            profesional.setValorConsulta(valorConsulta);
            profesional.setDescripcionEspecialidad(descripcionEspecialidad);

            profesionalRepositorio.save(profesional);

        }
    }

    public Profesional getOne(String idProfesional) {
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

    private void validarModificar(String mail, String nombre, String apellido, String dni, String telefono, LocalDate fechaNacimiento) throws MiException {
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
        //validar tambien el id de obra social que el ususario selecione una
    }

    final boolean ComprobarString(String cadena, String regex) {
        return cadena.matches(regex);
    }

    //Crear jornada laboral
    @Transactional
    public List<JornadaLaboral> crearJ(Profesional profesional, String diaSemana, LocalTime horaInicio,
            LocalTime horaFin, Long duracion) throws MiException {
        if (profesional != null) {

            List<JornadaLaboral> jornadas = new ArrayList();
            JornadaLaboral jornada = jornadaServicio.crearJornadaLaboral(profesional, diaSemana, horaInicio, horaFin, duracion);
            jornadas.add(jornada);
            return jornadas;
        } else {
            return null;
        }
    }

    //Listar las jornadas laborales de X profesional
    public List<JornadaLaboral> listarJornadas(Profesional profesional) throws MiException {

        if (profesional != null) {
            return profesional.getJornadaLaboral();
        } else {
            throw new MiException("No disponible");
        }
    }

    @Transactional
    public void modificarJornada(Profesional profesional, String id_jornada, String diaSemana,
            LocalTime horaInicio, LocalTime horaFin, Long duracionTurno) throws MiException {

        if (profesional != null) {
            jornadaServicio.modificarJornada(profesional, id_jornada, diaSemana, horaInicio, horaFin, duracionTurno);
        }

    }

    @Transactional
    public void eliminarJornada(String id_jornada) throws MiException {
        JornadaLaboral jornada = jornadaLaboralRepositorio.getOne(id_jornada);
        jornadaLaboralRepositorio.delete(jornada);
    }

//    public List<Profesional> listarProfesionales() {
//
//        List<Profesional> profesionales = profesionalRepositorio.findAll();
//        return profesionales.stream().collect(Collectors.toList());
//    }
    @Transactional
    public void eliminarProfesional(String id_profesional) throws MiException {

        profesionalRepositorio.deleteById(id_profesional);

    }

    public void actualizarReputacion(Double promedio, Profesional profesional) {
        profesional.setReputacion(promedio);
        profesionalRepositorio.save(profesional);
    }

//-------------------- contraseña ------------------
    @Transactional
    public void cambiarContrasenia(String idProfesional, String contraVieja, String contraNueva, String contraComparar) throws MiException {

        Profesional profesional = profesionalRepositorio.getOne(idProfesional);
        validarContraseña(contraVieja, profesional.getPassword(), contraNueva, contraComparar);

        profesional.setPassword(new BCryptPasswordEncoder().encode(contraNueva));

        profesionalRepositorio.save(profesional);

    }

    private void validarContraseña(String contraVieja, String contraBS, String contraNueva, String contraComparar) throws MiException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(contraVieja, contraBS)) {
            throw new MiException("Ingrese devuelta su contraseña");
        }
        if (!contraNueva.equals(contraComparar)) {
            throw new MiException("No coiciden las nuevas contraseñas");
        }
    }

    // ---------------------- BUSCAR POR MAIL ----------------------
    
    public Profesional buscarPorMail(String mail) {
        Profesional profesional = profesionalRepositorio.BuscarPorEmail(mail);
        
        if(profesional != null){
            return profesional;
        }else{
            return null;
        }

    }

}
