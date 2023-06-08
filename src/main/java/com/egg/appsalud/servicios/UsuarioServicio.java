package com.egg.appsalud.servicios;


import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.EspecialidadRepositorio;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;
    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Usuario usuario = pacienteRepositorio.BuscarPorEmail(mail);

        if (usuario == null) {
            usuario = profesionalRepositorio.BuscarPorEmail(mail);
        }

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);
            session.setAttribute("mail", mail);

            return new User(usuario.getMail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }

    }

//------------------------------------Profesional-----------------------------------
    public void CrearProfesional(String mail, String password, String nombre, String apellido,
                                 String dni, LocalDate fechaNacimiento, String telefono, String matricula,
                                 Integer especialidad, Double valorConsulta, String descripcionEspecialidad) throws MiException{
        
        profesionalServicio.crearProfesional(mail, password, nombre, apellido, dni, fechaNacimiento, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
    }

    public List<Profesional> listarProfesionales() {
        return profesionalServicio.listarProfesionales();
    }

//--------------------------------Paciente--------------------------------
    public void CrearPaciente(MultipartFile archivo, String mail, String password, Integer idObraSocial,
            String nroObraSocial, String nombre, String apellido, String dni, LocalDate fechaNacimiento,
            String telefono) throws MiException, IOException {

        pacienteServicio.CrearPaciente(archivo, mail, password, idObraSocial, nroObraSocial, nombre, apellido, dni, fechaNacimiento, telefono);
    }

    public List<Paciente> listarPacientes() {
        return pacienteServicio.listarPacientes();
    }

    public Paciente buscarPorId(String idPaciente) throws MiException {
        return pacienteServicio.buscarPorId(idPaciente);
    }

    public void modificarPaciente(MultipartFile archivo, String id_paciente, String mail, String nombre,
            String apellido, String dni, LocalDate fechaNacimiento, String telefono, String nroObraSocial, Integer idObraSocial) throws MiException, IOException {
        pacienteServicio.modificarPaciente(archivo, id_paciente, mail, nombre, apellido, dni, fechaNacimiento, telefono, nroObraSocial, idObraSocial);
    }

    public Paciente getOne(String id_paciente) {
        return pacienteServicio.getOne(id_paciente);
    }

    public void eliminarPaciente(String id_paciente) throws MiException {
        pacienteServicio.eliminarPaciente(id_paciente);
    }

//-----------------Obra Social----------------------------
    public void CrearObraSocial(String nombre) throws MiException {
        obraSocialServicio.CrearObraSocial(nombre);
    }

    public List<ObraSocial> listarObraSociales() {
        return obraSocialServicio.listarObraSocial();
    }

//-----------------------------Especialiada----------------------------
    @Transactional
    public void CrearEspecialiadad(String nombre){
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre);
        especialidadRepositorio.save(especialidad);
            
    }
    
    public List<Especialidad> listarEspecialidad(){
        return especialidadRepositorio.findAll();
    }


//----------------- Reestablecer contraseña ----------------------------    
    public void solicitarContra(String mail) throws MiException{
        Profesional profesional = profesionalServicio.buscarPorMail(mail);

        Paciente paciente = pacienteServicio.buscarPorMail(mail);

        if (profesional != null) {
            String nuevaContra = "12345678";

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashContra = encoder.encode(nuevaContra);

            profesional.setPassword(hashContra);
            profesionalRepositorio.save(profesional);
        } else if (paciente != null) {
            String nuevaContra = "12345678";

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashContra = encoder.encode(nuevaContra);

            paciente.setPassword(hashContra);
            pacienteRepositorio.save(paciente);
        }else{
            throw new MiException("No se encontró ningún usuario con el correo electrónico proporcionado");
        }


    }

}
