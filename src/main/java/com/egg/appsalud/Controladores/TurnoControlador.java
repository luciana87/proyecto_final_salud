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


}
