package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.JornadaLaboralServicio;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.TurnoServicio;
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
    private PacienteServicio pacienteServicio;

    @Autowired
    JornadaLaboralServicio jornadaServicio;
    @Autowired
    private TurnoServicio turnoServicio;

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
            return "/registro-profesional.html";
        }
        return "redirect:/";
    }

    @GetMapping("/inicio")
    public String inicio(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        return "inicio-profesional.html";
        
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable String id, ModelMap model) {

        Profesional profesional = profesionalServicio.getOne(id);
        model.put("profesional", profesional);
        return "modificar-profesional.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, String mail, String password, String nombre, String apellido,
                                       String dni, String fechaNacimiento, String telefono, String matricula, String especialidad,
                                       Double valorConsulta, String descripcionEspecialidad, ModelMap modelo) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {
            profesionalServicio.modificarProfesional(id, mail, password,
                    nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar-profesional.html";
        }
        return "index.html";//Vista inicio profesional no index @PathVariable String id_profesional,
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

    @PostMapping("/fechas")
    private String calcularRangoFechas(@RequestParam String inicio, @RequestParam String fin, HttpSession session) throws MiException {
        LocalDate inicioRango = LocalDate.parse(inicio, formatter);
        LocalDate finRango = LocalDate.parse(fin, formatter);

        turnoServicio.crearTurno(session.getId(), inicioRango,finRango);


        System.out.println("Llego llego " + inicioRango + " " + finRango);
        return "calificar-profesional.html";
    }

    //    @PostMapping("/calificar/turno/{id}")
//    public String calificarMedico(@RequestParam String id, @RequestParam Integer calificacion, ModelMap modelo){
//
//
//        try {
//            pacienteServicio.calificar(id,calificacion);
//            modelo.put("exito", "El profesional fue calificado correctamente");
//        } catch (MiException ex) {
//            modelo.put("error", ex.getMessage());
//            return "/calificar-profesional.html";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return "redirect:/inicio-paciente-2";
//    }


    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);

        return "lista-profesional.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }

    @GetMapping("/eliminar/{id_profesional}")
    public String eliminarPaciente(@PathVariable String id_profesional, ModelMap modelo) {

        try {
            profesionalServicio.eliminarProfesional(id_profesional);
            modelo.put("exito", "Se elimino el Paciente correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            
        }
        return "redirect:../lista";
    }

    
}
