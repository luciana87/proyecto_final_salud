package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
                           @RequestParam String password, @RequestParam String fechaNacimiento, @RequestParam String dni,
                           @RequestParam Long telefono, ModelMap modelo, MultipartFile archivo){

        System.out.println("Se registro");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto
        try {
        pacienteServicio.CrearPaciente(archivo,mail, password, nombre, apellido, dni, fechaNac,telefono);
        modelo.put("exito", "El paciente fue creado correctamente");
         } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "/registro.html";
        }
        return "redirect:/";
    }

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(){
        return "registro.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista_paciente.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }


    @GetMapping("/obtener/{idPaciente}")
    public String obtenerPorId (@PathVariable String idPaciente, ModelMap modelo) {
        try {

            Paciente paciente = pacienteServicio.buscarPorId(idPaciente);
            modelo.put("paciente", paciente);//No deberia mostrasr usuario y contraseÃ±a
            return "datos_paciente.html";

        } catch (MiException e){

            modelo.put("error", e.getMessage());
            return "index.html";//Retorna vista a definir

        }
    }
    
    @GetMapping("/modificar/{id_paciente}")
    public String mostrarFormularioModificar(@PathVariable String id_paciente, ModelMap model){
        
        model.put("paciente", pacienteServicio.getOne(id_paciente));
        return "vista_form_modificar_profesional.html";
    }

    @PostMapping("/modificar/{id_paciente}")
    public String modificarPaciente(@PathVariable String id_paciente, String mail, String password, String nombre, String apellido,
            String dni, String fechaNacimiento, long telefono, ModelMap modelo, MultipartFile archivo) {
        
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        
        try {
            
            pacienteServicio.modificarPaciente(archivo,id_paciente, mail, password,
                    nombre, apellido, dni, fechaNac, telefono);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "vista_form_modificar_profesional.html";
        }
        return "vista_inicio_paciente.html";
    }
}
