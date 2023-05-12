package com.egg.appsalud.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(){
        return "paciente_form.html";
    }
}
