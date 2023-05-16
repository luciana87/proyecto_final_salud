package com.egg.appsalud.controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.ServicioPaciente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paciente")
public class ControladorPaciente {

    @Autowired
    private ServicioPaciente pacienteSer;

    @GetMapping("/registrar")
    public String registrar() {
        return "paciente_form.html";
    }

    @PostMapping("/ingreso")
    public String registro(@RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String dni, @RequestParam Integer edad, @RequestParam String mail,
            @RequestParam String password, @RequestParam Long telefono, ModelMap modelo) throws MiException {

        try {
            pacienteSer.CrearPaciente(mail, password, nombre, apellido, dni, edad, telefono);
            modelo.put("exito", "la noticia fue cargada correctamente");
            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "paciente_form.html";

        }

    }

    @GetMapping("/mostrarTodas")
    public String pacienteMostrar(ModelMap modelo) {

        List<Paciente> pacientes = pacienteSer.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista_pacientes.html";
    }

    @GetMapping("/modificar/{id_paciente}")
    public String mostrarFormularioModificar(@PathVariable String id_paciente, ModelMap model){
        
        model.put("paciente", pacienteSer.getOne(id_paciente));
        return "paraModificar.html";
    }

    @PostMapping("/modificar/{id_paciente}")
    public String modificarPaciente(@PathVariable String id_paciente, String mail, String password, String nombre, String apellido,
            String dni, Integer edad, long telefono, ModelMap modelo) {
        try {
            pacienteSer.modificarPaciente(id_paciente, mail, password,
                    nombre, apellido, dni, edad, telefono);
            modelo.put("exito", "la noticia fue cargada correctamente");
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "paraModificar.html";
        }
        return "lista_pacientes.html";
    }

//@GetMapping("/modificar/{id_paciente}")
//    public String solicitudParaModificar(@PathVariable String id_paciente, ModelMap modelo) {
//        
//        modelo.put("paciente", pacienteSer.getOne(id_paciente));
//        return "paraModificar.html";
//    }
//
//    @PostMapping("/modificar/{id_paciente}")
//    public String actualizarPaciente(@PathVariable String id_paciente, String mail, String password, String nombre, String apellido, String dni, Integer edad, Long telefono) throws MiException {
//        pacienteSer.modificarPaciente(id_paciente, mail, password, nombre, apellido, dni, edad, telefono);
//        return "mostrarTodas.html";
//    }
//
//    @PatchMapping("/modifica/{id_paciente}")
//    public String modificar(@PathVariable String id_paciente, @ModelAttribute Paciente paciente, RedirectAttributes redirectAttributes) {
//
//        try {
//            pacienteSer.modificarPaciente(id_paciente, paciente.getMail(), paciente.getPassword(),
//                    paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getEdad(), paciente.getTelefono());
//            redirectAttributes.addFlashAttribute("exito", "Noticia modificada con éxito");
//            return "redirect:/modificarSolicitud/" + id_paciente;
//        } catch (MiException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/modificarSolicitud/" + id_paciente;
//        }
//    }
}
