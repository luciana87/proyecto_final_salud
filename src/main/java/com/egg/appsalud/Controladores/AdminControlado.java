/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/admin")
public class AdminControlado {
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "admin_prueba.html";
    }
    
    
//--------------------------------Paciente-------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/crearPaciente")
    public String crearPaciente(){
        return "registro-paciente.html";//cambiar a una version del form para admin
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaPacientes")
    public String listarPaciente(ModelMap modelo){
        List<Paciente> pacientes = usuarioServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista-paciente.html"; 
    }
    
//--------------------------------Profesional------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaProfesionales")
    public String listarProfesional(ModelMap modelo){
        List<Profesional> profesionales = usuarioServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);

        return "lista-profesional.html"; 
    }
//------------------------------ObraSocial-----------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/CrearObraSocial")
    public String CrearObraSocial (){
        return "registro-os.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaObraSociales")
    public String listarObraSociales(ModelMap modelo){
        List<ObraSocial> ObraSociales = usuarioServicio.listarObraSociales();
        modelo.addAttribute("obraSociales", ObraSociales);
        return "lista-obraSocial";
    }
}
