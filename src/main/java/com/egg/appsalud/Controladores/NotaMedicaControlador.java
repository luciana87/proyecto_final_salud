/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.NotaMedicaServicio;
import com.egg.appsalud.servicios.TurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author carel
 */
@Controller
@RequestMapping("/nota-medica")
public class NotaMedicaControlador {

    @Autowired
    private NotaMedicaServicio notaMedicaServicio;
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @GetMapping("/registrar/{TurnoId}") //Retorna vista para registrarse
    public String registrar(@PathVariable Integer TurnoId, ModelMap modelo) throws MiException{
        System.out.println("hISTORIAS: "+TurnoId);
        
        Turno turno = turnoServicio.listarTurnosporId(TurnoId);
        //notaMedicaServicio.crearNotaMedica(turno.getProfesional().getId(), descripcion, turno.getPaciente().getId());
        System.out.println("hISTORIANDO: "+ turno.getProfesional().getId());
        modelo.addAttribute("turno", turno);

        return "registro-nota-medica.html";
    }
    
    @PostMapping("/registro/{TurnoId}")
    public String registro(@PathVariable Integer TurnoId, @RequestParam String descripcion, ModelMap modelo){

        //LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto
        System.out.println("Estoy en registro y: "+TurnoId);
        
        Turno turno = turnoServicio.listarTurnosporId(TurnoId);
        System.out.println("estoy registro: "+turno.getProfesional().getApellido());
        //notaMedicaServicio.crearNotaMedica(turno.getProfesional().getId(), descripcion, turno.getPaciente().getId());
        //Turno turno = turnoServicio.listarTurnosporId(IdProfesional);
        try {
            //notaMedicaServicio.crearNotaMedica(IdProfesional, descripcion, IdPaciente);
            notaMedicaServicio.crearNotaMedica(turno.getProfesional().getId(), descripcion, turno.getPaciente().getId());
        
        modelo.put("exito", "El paciente fue creado correctamente");
         } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "/registro.html";
        }
        return "redirect:/inicio";
    }  
        
    
    
}

