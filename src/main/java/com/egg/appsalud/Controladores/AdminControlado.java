/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;


import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




@Controller
@RequestMapping("/admin")
public class AdminControlado {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoServicio turnoServicio;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard")
    public String panelAdministrativo (ModelMap modelo){
        List<ObraSocial>ObrasSociales = obraSocialServicio.listarObraSocial();
        modelo.put("obraSociales", ObrasSociales);
        return "admin_prueba.html";
    }
    
    
//--------------------------------Paciente-------------------------------------
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/crearPaciente")
    public String crearPaciente (ModelMap modelo){
        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
        modelo.addAttribute("obrasSociales", obrasSociales);
        return "registro-paciente.html";
    }

    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @PostMapping ("/dashboard/crearPaciente")
    public String crearPaciente (@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
                           @RequestParam String password, @RequestParam String idObraSocial, @RequestParam String nroObraSocial,
                           @RequestParam String fechaNacimiento, @RequestParam String dni,
                           @RequestParam String telefono, ModelMap modelo, MultipartFile archivo) {


        Integer idObraSocialInt = Integer.valueOf(idObraSocial);
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter); //Convierte el String de fechaNacimiento a LocalDate, si pongo directamente tipo LocalDate genera conflicto

        try {
            pacienteServicio.CrearPaciente(archivo, mail, password, idObraSocialInt, nroObraSocial, nombre, apellido, dni, fechaNac, telefono);
            modelo.put("exito", "El paciente fue creado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
            modelo.addAttribute("obrasSociales", obrasSociales);//vuelvo a enviar la lista de obrasociales
            return "registro-paciente.html";
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return "redirect:/inicio";
    }

    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaPacientes")
    public String listarPaciente (ModelMap modelo){
        List<Paciente> pacientes = usuarioServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista-pacienteAdmin.html"; 
    }
    
    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaPacientes/modificar/{id_paciente}")
    public String mostrarFormularioModificar (@PathVariable String id_paciente, ModelMap model) {
        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
        model.put("paciente", usuarioServicio.getOne(id_paciente));
        model.put("obrasSociales", obrasSociales);
        return "modificar-pacienteAdmin.html";
    }

            
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @PostMapping ("/dashboard/listaPacientes/modificar/{id_paciente}")
    public String formularioModificarPaciente (@PathVariable String id_paciente, String mail, String nombre, String apellido, String dni, String fechaNacimiento,
                                              String telefono, String nroObraSocial,Integer idObraSocial, ModelMap modelo, MultipartFile archivo){
        
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        
        try {
        usuarioServicio.modificarPaciente(archivo, id_paciente, mail, nombre, apellido, dni, fechaNac, telefono, nroObraSocial,idObraSocial);
        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaPacientes/modificar/{id_paciente}";
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return "redirect:/admin/dashboard/listaPacientes";
    }
    
    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaPacientes/eliminar/{id_paciente}")
    public String eliminarPaciente (@PathVariable String id_paciente, ModelMap modelo){
        try {
            usuarioServicio.eliminarPaciente(id_paciente);
            modelo.put("exito", "Se elimino el Paciente correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaPacientes";
        }
        return "redirect:/admin/dashboard/listaPacientes";
    }
    


//--------------------------------Profesional------------------------------------



    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/crearProfesional")
    public String crearProfesional(){
        
        return "registro-profesional.html";
    }
    
    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @PostMapping ("/dashboard/crearProfesional")
    public String crearProfesional (@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,

            @RequestParam String password, @RequestParam String fechaNacimiento, @RequestParam String dni,
            @RequestParam String telefono, @RequestParam String matricula, @RequestParam String especialidad,
            @RequestParam Double valorConsulta, @RequestParam String descripcionEspecialidad, ModelMap modelo){
        
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        try {
            usuarioServicio.CrearProfesional(mail, password, nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "El profesional fue creado correctamente");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/dashboard";
    }


    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaProfesionales")
    public String listarProfesional (ModelMap modelo){
        List<Profesional> profesionales = usuarioServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);

        return "lista-profesionalAdmin.html";
    }

    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaProfesionales/modificar/{id}")
    public String mostrarFormularioModificarProfesional (@PathVariable String id, ModelMap modelo) {

            Profesional profesional = profesionalServicio.getOne(id);
            modelo.put("profesional", profesional);

            return "modificar-profesionalAdmin.html";
    }
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @PostMapping ("/dashboard/listaProfesionales/modificar/{id}")
    public String modificarProfesional (@PathVariable String id, String mail, String password, String nombre, String apellido,
                                       String dni, String fechaNacimiento, String telefono, String matricula, String especialidad,
                                       Double valorConsulta, String descripcionEspecialidad, ModelMap modelo) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {
            profesionalServicio.modificarProfesional(id, mail, password, nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/dashboard/listaProfesionales/modificar/{id}";
        }
        return "redirect:/admin/dashboard/listaProfesionales";
    }


    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaProfesionales/eliminar/{id}")
    public String eliminarProfesional (@PathVariable String id, ModelMap modelo){
        try {
            profesionalServicio.eliminarProfesional(id);
            modelo.put("exito", "Se elimino el profesional correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaProfesionales";
        }
        return "redirect:/admin/dashboard/listaProfesionales";
    }


//------------------------------ObraSocial-----------------------------------------


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/CrearObraSocial")
    public String CrearObraSocial (){
        return "registro-os.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping ("/dashboard/crearObraSocial")
    public String crearObraSocial(@RequestParam String nombre, ModelMap modelo) {
        try {
            obraSocialServicio.crearObraSocial(nombre);
            modelo.put("exito", "La obra social fue creada correctamente");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            System.out.println(e.getMessage());
            return "registro-os.html";
        }
        return "redirect:/inicio";
    }
    
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/dashboard/listaObraSociales")
    public String listarObraSociales (ModelMap modelo){
        List<ObraSocial> ObraSociales = usuarioServicio.listarObraSociales();
        modelo.addAttribute("obraSociales", ObraSociales);
        return "lista-oSocialAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaObraSociales/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable Integer id, ModelMap modelo) {
        try {
            ObraSocial obraSocial = obraSocialServicio.getOne(id);
            modelo.put("obraSocial", obraSocial);

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/admin/dashboard/listaObraSociales";
        }
        return "modificar_os.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/dashboard/listaObraSociales/modificar/{id}")
    public String formularioModificarObraSocial (@PathVariable Integer id, String nombre, ModelMap modelo) {
        try {
            obraSocialServicio.modificarObraSocial (id,nombre);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaObraSociales/modificar/{id}";
        }
        return "redirect:/admin/dashboard/listaObraSociales";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaObraSociales/eliminar/{id}")
    public String eliminarObraSocial(@PathVariable Integer id, ModelMap modelo){
        try {
            obraSocialServicio.eliminarObraSocial(id);
            modelo.put("exito", "Se eliminó la obra social correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaObraSociales";
        }
        return "redirect:/admin/dashboard/listaObraSociales";
    }


    //--------------------------------TURNOS------------------------------------

    @GetMapping("/dashboard/listaTurno")
    public String listarTurnos (ModelMap modelo) {
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);

        return "lista-turnosAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaTurno/eliminar/{id}")
    public String eliminarTurno(@PathVariable Integer id, ModelMap modelo){
        try {
            turnoServicio.eliminarTurno(id);
            modelo.put("exito", "Se eliminó el turno correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/dashboard/listaTurno";
        }
        return "redirect:/admin/dashboard/listaTurno";
    }

    @PostMapping("/dashboard/buscarTurnos")
    public String buscarTurnos(String idProfesional,String fecha,String horario,  String nombre, Double valorConsulta, ModelMap modelo){

        LocalTime horaioParse = null;
        LocalDate fechaParse = null;


        if(!fecha.isEmpty()){
            fechaParse = LocalDate.parse(fecha, formatter);
        }

        if(!horario.isEmpty()){
            horaioParse = LocalTime.parse(horario);
        }


        List<Turno>ListaTurnoFiltro = turnoServicio.buscarTurnosFiltro(idProfesional,fechaParse ,horaioParse , nombre, valorConsulta, EstadoTurno.DISPONIBLE);
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("turnos", ListaTurnoFiltro);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);
        modelo.addAttribute("profesionalId", idProfesional);

        return "lista-turnosAdmin.html";

    }



}
