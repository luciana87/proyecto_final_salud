package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.TurnoServicio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {
@Autowired
    private TurnoServicio turnoServicio;
    
    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @Autowired
    private PacienteServicio pacienteServicio;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(ModelMap modelo) {
        
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        return "registro-turno.html";
    }

    
    @PostMapping("/registroT")
    public String registro(@RequestParam Paciente paciente, @RequestParam Profesional profesional, @RequestParam String fechaTurno, @RequestParam String horario, ModelMap modelo) {

        LocalDate fechaTurno1 = LocalDate.parse(fechaTurno, formatter); 

        try {
            turnoServicio.crearTurno(paciente, profesional, fechaTurno1, horario);
            modelo.put("exito", "El profesional fue creado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "/registro-turno.html";
        }
        return "redirect:/";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Turno> turnos = turnoServicio.listarTurnos();
        modelo.addAttribute("turnos", turnos);
        
        return "lista-turno.html"; 
    }
}
