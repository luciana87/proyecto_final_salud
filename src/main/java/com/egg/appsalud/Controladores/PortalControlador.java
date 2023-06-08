package com.egg.appsalud.Controladores;


import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.servicios.PacienteServicio;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private PacienteServicio pacienteServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate

    @GetMapping("/")
    public String index(){
        return "index.html"; //Retorna vista del index.html
    }
    
//    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: año-mes-dia del LocalDate
    
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if (error != null){
            modelo.put("error","Usuario o contraseña invalidos");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMIN', 'ROLE_PROFESIONAL')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("PROFESIONAL")) {
            return "redirect:/profesional/inicio";
        }

        if (logueado.getRol().toString().equals("PACIENTE")) {
            return "redirect:/paciente/inicio";
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista-paciente.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }


    @GetMapping("/prueba")
    public String obtener(){
        return "form-crear-turnos.html";
    }

    @PostMapping("/prueba")
    public  String prueba(@RequestParam (required = false) String lunes, @RequestParam (required = false) String martes,
                          @RequestParam (required = false) String miercoles){

        String dias = lunes + martes + miercoles;

        System.out.println(lunes +" "+ martes + miercoles);
        return "form-crear-turnos.html";
    }
    

}



