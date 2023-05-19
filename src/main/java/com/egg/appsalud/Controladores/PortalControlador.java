package com.egg.appsalud.Controladores;

import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class PortalControlador {

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
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: año-mes-dia del LocalDate
    
    
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}
