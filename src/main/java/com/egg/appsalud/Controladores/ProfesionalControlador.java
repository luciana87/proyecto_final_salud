package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private PacienteServicio pacienteServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate


    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(){
        return "registro-profesional.html";
    }


    @PostMapping("/registroP")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
                           @RequestParam String password, @RequestParam String fechaNacimiento, @RequestParam String dni,
                           @RequestParam Long telefono, @RequestParam String matricula,@RequestParam String especialidad,
                           @RequestParam Double valorConsulta,@RequestParam String descripcionEspecialidad, ModelMap modelo/*, MultipartFile archivo*/){

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto

        try {
            profesionalServicio.crearProfesional(/*archivo,*/mail, password, nombre, apellido, dni, fechaNac,telefono,
                    matricula,especialidad,valorConsulta,descripcionEspecialidad);
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
    public String mostrarFormularioModificar(@PathVariable String id, ModelMap model){

        Profesional profesional = profesionalServicio.getOne(id);
        model.put("profesional", profesional);
        return "modificar-profesional.html";
    }
    @PostMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, String mail, String password, String nombre, String apellido,
                                       String dni, String fechaNacimiento, Long telefono, String matricula, String especialidad,
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
        return "index.html";//Vista inicio profesional no index
    }


//    @PostMapping("/calificar/profesional/{id}")
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

}
