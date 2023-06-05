package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ObraSocialRepositorio;
import com.egg.appsalud.servicios.ObraSocialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/obra-social")
public class ObraSocialControlador {

    @Autowired
    ObraSocialServicio obraSocialServicio;

    @Autowired
    private ObraSocialRepositorio obraSocialRepositorio;


    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(){
        return "registro-os.html";
    }


    @PostMapping("/registro-os")
    public String registro(@RequestParam String nombre, ModelMap modelo){

        try {
            obraSocialServicio.crearObraSocial(nombre);
            modelo.put("exito", "La obra social fue creada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "/registro-os.html";
        }
        return "redirect:/";
    }


    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable Integer id, ModelMap model) throws MiException {

        ObraSocial obraSocial = obraSocialServicio.getOne(id);
        model.put("obraSocial", obraSocial);
        return "modificar_os.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarPaciente(@PathVariable Integer id, String nombre, ModelMap modelo) {

        try {

            obraSocialServicio.modificarObraSocial(id,nombre);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_os.html";
        }
        return "index.html";
    }
}
