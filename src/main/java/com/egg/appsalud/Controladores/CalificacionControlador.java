package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.servicios.CalificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping ("/calificacion")
public class CalificacionControlador {

    @Autowired
    private CalificacionServicio calificacionServicio;


    @GetMapping("/registrar/{turnoId}") //localhost:8080/calificacion/registrar/{turnoId} --> Ruta a la que me lleva para calificar al profesional cuando toco en 'calificar' de la tabla de turnos
    public String registrar(ModelMap modelo, @PathVariable String turnoId){


        return "calificar-profesional.html"; // Retorna la vista para calificar al profesional
    }

}
