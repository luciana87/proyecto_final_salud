package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.servicios.TurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/paciente")           //localhost:8080/turnos/paciente
    public String listar(HttpSession session, ModelMap modelo){

        Paciente paciente = (Paciente) session.getAttribute("usuariosession");//Obtengo la información de la sesión
        List<Turno> turnos = turnoServicio.listarTurnosPorPacientes(paciente);
        modelo.addAttribute("turnos", turnos);

        return "lista-turnos.html"; //Retorna vista con todos los turnos de un paciente especifico persistidos en la DB
    }
}
