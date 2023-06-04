/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.ObraSocialServicio;
import com.egg.appsalud.servicios.UsuarioServicio;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;




@Controller
@RequestMapping("/admin")
public class AdminControlado {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo){
        List<ObraSocial>ObrasSociales = obraSocialServicio.listarObraSocial();
        modelo.put("obraSociales", ObrasSociales);
        return "admin.html";
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

        return "lista-pacienteAdmin.html"; 
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listaPacientes/modificar/{id_paciente}")
    public String mostrarFormularioModificar(@PathVariable String id_paciente, ModelMap model){
        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
        model.put("paciente", usuarioServicio.getOne(id_paciente));
        model.put("obrasSociales", obrasSociales);
        return "modificar-paciente.html";
    }
            
            
            
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/listaPacientes/modificar/{id_paciente}")
    public String modificarPaciente(@PathVariable String id_paciente, String mail, String nombre, String apellido,

            String dni, String fechaNacimiento, String telefono, String nroObraSocial,Integer idObraSocial, ModelMap modelo, MultipartFile archivo) throws MiException, IOException{
        
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        
        try {
        usuarioServicio.modificarPaciente(archivo, id_paciente, mail, nombre, apellido, dni, fechaNac, telefono, nroObraSocial,idObraSocial);
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/listaPacientes/modificar/{id_paciente}";
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return "redirect:/dashboard/listaPacientes";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/listaPacientes/eliminar/{id_paciente}")
    public String eliminarPaciente(@PathVariable String id_paciente, ModelMap modelo){
        try {
            usuarioServicio.eliminarPaciente(id_paciente);
            modelo.put("exito", "Se elimino el Paciente correctamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/dashboard/listaPacientes";
        }
        return "redirect:/dashboard/listaPacientes";
    }
    
    
//--------------------------------Profesional------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/crearProfesional")
    public String crearProfesional(){
        
        return "registro-profesional.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/dashboard/crearProfesional")
    public String crearProfesional(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,

            @RequestParam String password, @RequestParam String fechaNacimiento, @RequestParam String dni,
            @RequestParam String telefono, @RequestParam String matricula, @RequestParam String especialidad,
            @RequestParam Double valorConsulta, @RequestParam String descripcionEspecialidad, ModelMap modelo){
        
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        try {
            usuarioServicio.CrearProfesional(mail, password, nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
        } catch (MiException e) {
            System.out.println(e.getMessage());
            return "redirect:/inicio";
        }
        
        return "redirect:/inicio";
    }
    
    
    
    
    
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
