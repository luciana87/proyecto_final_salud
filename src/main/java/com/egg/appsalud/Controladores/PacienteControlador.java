package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.*;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hibernate.annotations.Parameter;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired

    private PacienteServicio pacienteServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private CalificacionServicio calificacionServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private TurnoServicio turnoServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(ModelMap modelo) {

        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
        modelo.addAttribute("obrasSociales", obrasSociales);

        return "registro-paciente.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,

            @RequestParam String password, @RequestParam String idObraSocial, @RequestParam String nroObraSocial,
            @RequestParam String fechaNacimiento, @RequestParam String dni,
            @RequestParam String telefono, ModelMap modelo, MultipartFile archivo) throws IOException {


        Integer idObraSocialInt = Integer.valueOf(idObraSocial);
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto

        try {
            pacienteServicio.CrearPaciente(archivo, mail, password, idObraSocialInt, nroObraSocial, nombre, apellido, dni, fechaNac, telefono);
            modelo.put("exito", "El paciente fue creado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
            modelo.addAttribute("obrasSociales", obrasSociales);//vuelvo a enviar la lista de obrasociales
            return "registro-paciente.html";
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return "redirect:/inicio";
    }


    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo){
        //TODO: eliminar esto cuando este el listado de turnos es una prueba para ver si funciona
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        Paciente paciente = pacienteRepositorio.BuscarPorEmail(session.getAttribute("mail").toString());
        modelo.put("paciente", paciente);
        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);
        

        //obtengo el usuario logueado
//        Paciente logueado = (Paciente) session.getAttribute("usuariosession");
//        boolean tieneImagen= ((Paciente)logueado).tieneImagen(); //Casteo la variable 'logueado' de tipo usuario a tipo 'Paciente' para poder acceder al metodo 'tieneImagen()'
//        modelo.put("tieneImagen", tieneImagen); //Envío a la vista si posee o no imágen.

        return "inicio_paciente_2.html";
    }

    @GetMapping("/historialTurnos/{id}")
    public String listar(@PathVariable String id, ModelMap modelo) {
        List<Turno> turnos = turnoServicio.listarTurnosPaciente(id);
        modelo.addAttribute("turnos", turnos);

        return "historial-turnos-paciente.html";
    }

    @GetMapping("/obtener/{idPaciente}")
    public String obtenerPorIdS(@PathVariable String idPaciente, ModelMap modelo) {
        try {

            Paciente paciente = pacienteServicio.buscarPorId(idPaciente);
            modelo.put("paciente", paciente);//No deberia mostrasr usuario y contraseÃ±a
            return "datos_paciente.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            return "index.html";//Retorna vista a definir

        }
    }

    @GetMapping("/modificar/{id_paciente}")
    public String mostrarFormularioModificar(@PathVariable String id_paciente, ModelMap model) {

        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();

        model.put("paciente", pacienteServicio.getOne(id_paciente));
        model.put("obrasSociales", obrasSociales);

        return "modificar-paciente.html";
    }

    @PostMapping("/modificar/{id_paciente}")
    public String modificarPaciente(@PathVariable String id_paciente, String mail, String nombre, String apellido,

            String dni, String fechaNacimiento, String telefono, String nroObraSocial, Integer idObraSocial, ModelMap modelo, MultipartFile archivo) {
        

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {

            
            pacienteServicio.modificarPaciente(archivo,id_paciente, mail,
                    nombre, apellido, dni, fechaNac, telefono,nroObraSocial,idObraSocial);

            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar-paciente.html";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/inicio";
    }
    
    @GetMapping("/eliminar/{id_paciente}")
    public String eliminarPaciente(@PathVariable String id_paciente, ModelMap modelo) {

        try {
            pacienteServicio.eliminarPaciente(id_paciente);
            modelo.put("exito", "Se elimino el Paciente correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            
        }
        return "redirect:../lista";
    }
    

    //-------------------------- TURNOS PACIENTE ------------------------------

    @GetMapping("/listaTurno")
    public String listarTurnos (@SessionAttribute("usuariosession") Paciente paciente, ModelMap modelo) {
        List<Turno> turnos = turnoServicio.listarTurnosPaciente(paciente.getId());
        modelo.addAttribute("turnos", turnos);

        return "historial-turnos-paciente.html";
    }

    @GetMapping("/buscarTurno")
    public String buscarTurnos(ModelMap modelo) {
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        List<Especialidad>listaEspecialidades = usuarioServicio.listarEspecialidad();
        modelo.addAttribute("especialidades", listaEspecialidades);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);
        return "lista-turnos.html";
    }

    @PostMapping("/buscarTurnos")
    public String buscarTurnos(String idProfesional,String fecha,String horario,  String nombre, Double valorConsulta,Double reputacion, Integer especialidad, ModelMap modelo){

        LocalTime horaioParse = null;
        LocalDate fechaParse = null;


        if(!fecha.isEmpty()){
            fechaParse = LocalDate.parse(fecha, formatter);
        }

        if(!horario.isEmpty()){
            horaioParse = LocalTime.parse(horario);
        }


        List<Turno>ListaTurnoFiltro = turnoServicio.buscarTurnosFiltro(idProfesional,fechaParse ,horaioParse , nombre, valorConsulta, EstadoTurno.DISPONIBLE,reputacion,especialidad);
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        List<Especialidad>listaEspecialidades = usuarioServicio.listarEspecialidad();
        modelo.addAttribute("especialidades", listaEspecialidades);
        modelo.addAttribute("turnos", ListaTurnoFiltro);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);

        return "lista-turnos.html";

    }

    @GetMapping("/calificar/turno/{id}")
    public String calificarMedico (@PathVariable Integer id, ModelMap modelo){
        Turno turno = turnoServicio.getOne(id);
        modelo.addAttribute("turno", turno);
        return "calificar-profesional.html";

    }

    @PostMapping("/calificar/turno/{id}")
    public String calificarMedico(@PathVariable Integer id, Integer calificacion, ModelMap modelo){
        calificacionServicio.crearCalificacion(id,calificacion);
        modelo.put("exito", "El profesional fue calificado correctamente");

        return "redirect:/inicio";
    }
    
    @GetMapping("/ReservarTurno/{id}")
    public String ReservarTurno(@PathVariable Integer id,@SessionAttribute("usuariosession") Paciente paciente){
        turnoServicio.CambiarTurnoReservado(id, paciente);
        return "redirect:/paciente/buscarTurno";
    }
    
    @GetMapping("/CancelarTurno/{id}")
    public String CancelarTurno(@PathVariable Integer id){
        turnoServicio.CambiarTurnoCancelado(id);
        return "redirect:/paciente/listaTurno";
    }
//-----------------------------------------contrasenia----------------------------------------------
    @GetMapping("/formCambiarContrasenia")
    public String CambiarContrasenia() {
        return "cambiar-contrasenia.html";
    }
    
    @PostMapping("cambiarcontasenia/{id}")
    public String cambiarContrasenia(@PathVariable String id, String contraVieja, String contraNueva, String contraComparar, ModelMap modelo) {
        try {
            pacienteServicio.cambiarContrasenia(id, contraVieja, contraNueva, contraComparar);
            modelo.put("exito", "Se cambio la contraseña");
        } catch (MiException e) {
            System.out.println(e.getMessage());
            modelo.put("error", e.getMessage());
            return "cambiar-contrasenia.html";
        }
        return "cambiar-contrasenia.html";
    }

}
