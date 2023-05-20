package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate


    @GetMapping("/modificar/{idProfesional}")
    public String mostrarFormularioModificarProfesional(@PathVariable String idProfesional, ModelMap model){

        model.put("profesional", profesionalServicio.getOne(idProfesional));
        return "vista_modificar_profesional.html";
    }
    @PostMapping("/modificar/{idProfesional}")
    public String modificarProfesional(@PathVariable String idProfesioanl, String mail, String password, String nombre, String apellido,
                                       String dni, String fechaNacimiento, Long telefono, String matricula, Especialidad especialidad,
                                       Double valorConsulta, String descripcionEspecialidad, ModelMap modelo) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {
            profesionalServicio.modificarProfesional(idProfesioanl, mail, password,
                    nombre, apellido, dni, fechaNac, telefono, matricula, especialidad, valorConsulta, descripcionEspecialidad);
            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "vista_modificar_profesional.html";
        }
        return "vista_inicio_profesional.html";
    }

}
