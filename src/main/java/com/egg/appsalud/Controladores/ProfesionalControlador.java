package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.*;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.servicios.*;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    JornadaLaboralServicio jornadaServicio;
    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private ObraSocialServicio obraSocialServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar() {
        return "registro-profesional.html";
    }

    @PostMapping("/registroP")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,

            @RequestParam String password, @RequestParam String fechaNacimiento, @RequestParam String dni,
            @RequestParam String telefono, @RequestParam String matricula, @RequestParam String especialidad,
            @RequestParam Double valorConsulta, @RequestParam String descripcionEspecialidad, ModelMap modelo/*, MultipartFile archivo*/) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto

        try {
            profesionalServicio.crearProfesional(/*archivo,*/mail, password, nombre, apellido, dni, fechaNac, telefono,
                    matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "El profesional fue creado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/profesional/inicio";
        }
        return "redirect:/profesional/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo){
        Profesional profesional = profesionalRepositorio.BuscarPorEmail(session.getAttribute("mail").toString());

        modelo.put("profesional", profesional);
        return "inicio_profesional.html";
        
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable String id, ModelMap model) {

        Profesional profesional = profesionalServicio.getOne(id);
        model.put("profesional", profesional);
        return "modificar-profesional.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, String mail, String nombre, String apellido,
                                       String dni, String fechaNacimiento, String telefono, String matricula, String especialidad,
                                       Double valorConsulta, String descripcionEspecialidad, ModelMap modelo) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {
            profesionalServicio.modificarProfesional(id, mail, nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";
        }
        return "redirect:/inicio";//Vista inicio profesional
    }

    @GetMapping("/crearJornada")
    public String crear() {
        return "formjornada.html";
    }

    @PostMapping("/jornadaLaboral")
    public String crearJornada(@RequestParam String diaSemana, @RequestParam LocalTime horaInicio, @RequestParam LocalTime horaFin,
            @RequestParam Long duracionTurno, ModelMap modelo, @SessionAttribute("usuariosession") Profesional profesional) throws MiException {

        try {
            List<JornadaLaboral> jornadas = profesionalServicio.crearJ(profesional, diaSemana, horaInicio, horaFin, duracionTurno);
            if (jornadas != null) {
                modelo.put("jornadas", jornadas);
                modelo.put("exito", "Jornada laboral creada exitosamente");
            } else {
                modelo.put("error", "No se pudo crear la jornada laboral");
            }

            return "formjornada.html";
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "No se pudo crear la jornada laboral");
            return "formjornada.html";
        }
    }

    @GetMapping("/jornada")
    public String mostrarJornadas(@SessionAttribute("usuariosession") Profesional profesional, ModelMap model) throws MiException {
        try {

            if (profesional != null) {
                List<JornadaLaboral> jornadasLaborales = profesionalServicio.listarJornadas(profesional);
                model.addAttribute("jornadasLaborales", jornadasLaborales);
            }
        } catch (MiException e) {
            model.put("error", "error con el id, estoy en controlador");
        }

        return "listaJornadas.html";
    }

    @GetMapping("/formularioModificar/{id_jornada}")
    public String modificarJornada(@PathVariable("id_jornada") String id_jornada, ModelMap modelo) {
        JornadaLaboral jornada = jornadaServicio.obtenerJornadaPorId(id_jornada);
        modelo.addAttribute("jornada", jornada);
        return "formJornadaEditar.html";
    }

    @PostMapping("/modificandoJornada/{id_jornada}")
    public String modificandoJornada(@SessionAttribute("usuariosession") Profesional profesional, @PathVariable("id_jornada") String id_jornada,
            @RequestParam String diaSemana, @RequestParam LocalTime horaInicio, @RequestParam LocalTime horaFin,
            @RequestParam Long duracionTurno, ModelMap modelo) throws MiException {
        try {
            profesionalServicio.modificarJornada(profesional, id_jornada, diaSemana, horaInicio, horaFin, duracionTurno);
            modelo.put("exito", "exito");
            return "listaJornadas.html";
        } catch (MiException e) {
            modelo.put("error", "error");
            return "formJornadaEditar.html";
        }
    }


    @GetMapping("/eliminar/{id_jornada}")
    public String eliminarJornada(@SessionAttribute("usuariosession") Profesional profesional,
                                  @PathVariable("id_jornada") String id_jornada, ModelMap modelo) throws MiException {
        try {
            profesionalServicio.eliminarJornada(profesional, id_jornada);
            modelo.put("exito", "Jornada eliminada");
            return "listaJornadas.html";
        } catch (MiException e) {
            modelo.put("error", "Error al eliminar la jornada");
            return "listaJornadas.html";
        }
    }

//    @PostMapping("/CrearTurno")
//    private String calcularRangoFechas(@RequestParam String inicio, @RequestParam String fin, HttpSession session) throws MiException {
//        LocalDate inicioRango = LocalDate.parse(inicio, formatter);
//        LocalDate finRango = LocalDate.parse(fin, formatter);
//
//        turnoServicio.crearTurno(session.getId(), inicioRango,finRango);
//
//
//        System.out.println("Llego llego " + inicioRango + " " + finRango);
//        return "calificar-profesional.html";
//    }

//    @GetMapping("/lista")
//    public String listar(ModelMap modelo) {
//        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
//        modelo.addAttribute("profesionales", profesionales);
//
//        return "lista-profesional.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
//    }

    @GetMapping("/darseDeBaja/{id}")
    public String darseDeBaja(@PathVariable String id, ModelMap modelo) {

        try {
            profesionalServicio.eliminarProfesional(id);
            modelo.put("exito", "Se eliminó el profesional correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/profesional/inicio";
        }
        return "redirect:/profesional/inicio";
    }


    //--------------------------------------------------- jornada------------------------------------------
    @GetMapping("/CrearJornadaTurnos")
    public String CrearJornadaTurno(@SessionAttribute("usuariosession") Profesional profesional, ModelMap modelo){
        try {
            List<Turno>listaTurnos = turnoServicio.ListarTurnoProfesional(profesional);
            List<JornadaLaboral>listaDeJornadas = jornadaServicio.listarJornadas(profesional);
            modelo.addAttribute("jornadas", listaDeJornadas);
            modelo.addAttribute("turnos", listaTurnos);
        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
        }
        return "formjornada.html";

    }
    @PostMapping("/jornadaLaboral")
    public String crearJornada(@RequestParam String diaSemana, @RequestParam LocalTime horaInicio, @RequestParam LocalTime horaFin,
                               @RequestParam Long duracionTurno, ModelMap modelo, @SessionAttribute("usuariosession") Profesional profesional) throws MiException {

        try {
            List<JornadaLaboral> jornadas = profesionalServicio.crearJ(profesional, diaSemana, horaInicio, horaFin, duracionTurno);
            List<JornadaLaboral>listaDeJornadas = profesionalServicio.listarJornadas(profesional);
            if (jornadas != null) {
                modelo.put("jornadas", listaDeJornadas);
                modelo.put("exito", "Jornada laboral creada exitosamente");
            } else {
                modelo.put("error", "No se pudo crear la jornada laboral");
            }

            return "redirect:/profesional/CrearJornadaTurnos";
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "No se pudo crear la jornada laboral");
            return "redirect:/profesional/CrearJornadaTurnos";
        }
    }

    @GetMapping("/eliminar/jornada/{id_jornada}")
    public String eliminarJornada(@SessionAttribute("usuariosession") Profesional profesional,
                                  @PathVariable("id_jornada") String id_jornada, ModelMap modelo) throws MiException {
        try {
            jornadaServicio.eliminarJornada(id_jornada);
            modelo.put("exito", "Jornada eliminada");
            return "redirect:/profesional/CrearJornadaTurnos";
        } catch (MiException e) {
            modelo.put("error", "Error al eliminar la jornada");
            return "redirect:/profesional/CrearJornadaTurnos";
        }
    }



    @GetMapping("/listaTurno")
    public String listarTurnos (@SessionAttribute("usuariosession") Profesional profesional, ModelMap modelo) {
        List<Turno> turnos = turnoServicio.ListarTurnoProfesional(profesional.getId());
        modelo.addAttribute("turnos", turnos);

        return "lista-turnos-profesional.html";
    }

    @GetMapping("/buscarTurno")
    public String buscarTurnos(ModelMap modelo) {
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);
        return "lista-turnos-profesional.html";
    }

    @PostMapping("/buscarTurno")
    public String buscarTurnos(String idProfesional, String fecha, String horario, String nombre, Double valorConsulta, ModelMap modelo){

        LocalTime horaioParse = null;
        LocalDate fechaParse = null;


        if(!fecha.isEmpty()){
            fechaParse = LocalDate.parse(fecha, formatter);
        }

        if(!horario.isEmpty()){
            horaioParse = LocalTime.parse(horario);
        }


        List<Turno>ListaTurnoFiltro = turnoServicio.buscarTurnosFiltro(idProfesional,fechaParse ,horaioParse , nombre, valorConsulta, EstadoTurno.DISPONIBLE);
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("turnos", ListaTurnoFiltro);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);

        return "lista-turnos-profesional.html";

    }
    
}
