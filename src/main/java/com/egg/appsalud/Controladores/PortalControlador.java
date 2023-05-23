package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class PortalControlador {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate

    @GetMapping("/")
    public String index(){
        return "index.html"; //Retorna vista del index.html
    }
    
//    @GetMapping("/registrar")
//    public String registrar(){
//        return "registro.html";
//    }
//    
    @Autowired
    private PacienteServicio pacienteServicio;
    
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
    public String inicio(HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("PROFESIONAL")) {
            return "inicio-profesional.html";
        }

        if (logueado.getRol().toString().equals("PACIENTE")) {
            return "inicio_paciente_2.html";
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista-paciente.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }
}


