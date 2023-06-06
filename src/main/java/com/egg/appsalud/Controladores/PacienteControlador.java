package com.egg.appsalud.Controladores;

import com.egg.appsalud.Enumerativos.Especialidad;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;

import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.servicios.ObraSocialServicio;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired

    private PacienteServicio pacienteServicio;
    @Autowired
    private ObraSocialServicio obraSocialServicio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Formateo los valores de ingreso a: aÃ±o-mes-dia del LocalDate

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
            @RequestParam String password, @RequestParam String idObraSocial, @RequestParam String nroObraSocial,
            @RequestParam String fechaNacimiento, @RequestParam String dni,
            @RequestParam String telefono, ModelMap modelo, MultipartFile archivo) throws IOException {

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

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(ModelMap modelo) {

        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();
        modelo.addAttribute("obrasSociales", obrasSociales);

        return "registro-paciente.html";
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        //TODO: eliminar esto cuando este el listado de turnos es una prueba para ver si funciona
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        List<ObraSocial> obraSociales = obraSocialServicio.listarObraSocial();
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        Paciente paciente = pacienteRepositorio.BuscarPorEmail(session.getAttribute("mail").toString());

        modelo.put("paciente", paciente);
        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("obraSociales", obraSociales);
        modelo.addAttribute("profesionales", profesionales);

        //obtengo el usuario logueado
//        Paciente logueado = (Paciente) session.getAttribute("usuariosession");
//        boolean tieneImagen= ((Paciente)logueado).tieneImagen(); //Casteo la variable 'logueado' de tipo usuario a tipo 'Paciente' para poder acceder al metodo 'tieneImagen()'
//        modelo.put("tieneImagen", tieneImagen); //Envío a la vista si posee o no imágen.
        return "inicio_paciente_2.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista-paciente.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }

    @GetMapping("/obtener/{idPaciente}")
    public String obtenerPorIdS(@PathVariable String idPaciente, ModelMap modelo) {
        try {

            Paciente paciente = pacienteServicio.buscarPorId(idPaciente);
            modelo.put("paciente", paciente);//No deberia mostrasr usuario y contraseÃ±a
            return "datos_paciente.html";

        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            return "index.html";//Retorna vista a definir

        }
    }

    @GetMapping("/modificar/{id_paciente}")
    public String mostrarFormularioModificar(@PathVariable String id_paciente, ModelMap model) {

        List<ObraSocial> obrasSociales = obraSocialServicio.listarObraSocial();

        model.put("paciente", pacienteServicio.getOne(id_paciente));
        model.put("obrasSociales", obrasSociales);

        return "modificar-paciente.html";
    }

    @PostMapping("/modificar/{id_paciente}")
    public String modificarPaciente(@PathVariable String id_paciente, String mail, String nombre, String apellido,
            String dni, String fechaNacimiento, String telefono, String nroObraSocial, Integer idObraSocial, ModelMap modelo, MultipartFile archivo) {

        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        try {

            pacienteServicio.modificarPaciente(archivo, id_paciente, mail,
                    nombre, apellido, dni, fechaNac, telefono, nroObraSocial, idObraSocial);

            modelo.put("exito", "Los datos fueron actualizados correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar-paciente.html";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/inicio";
    }

    @GetMapping("/eliminar/{id_paciente}")
    public String eliminarPaciente(@PathVariable String id_paciente, ModelMap modelo) {

        try {
            pacienteServicio.eliminarPaciente(id_paciente);
            modelo.put("exito", "Se elimino el Paciente correctamente.");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

        }
        return "redirect:../lista";
    }
    @PostMapping("cambiarcontasenia/{id}")
    public String cambiarContrasenia(@PathVariable String id, String contraVieja, String contraNueva, String contraComparar, ModelMap modelo){
        try {
            pacienteServicio.cambiarContrasenia(id, contraVieja, contraNueva, contraComparar);
        } catch (MiException e) {
            System.out.println(e.getMessage());
            modelo.put("error", e.getMessage());
            return "redirect:/inicio";
        }
        return "redirect:/inicio";
        
        
    }

    // Filtro de busqueda
    // Por especialidad
//    @GetMapping("/buscador")
//    public String irABuscar() {
//        return "inicio_paciente_2.html";
//    }
//
//    @PostMapping("/buscar")
//    public String buscarPorEspecialidad(Especialidad especialidad,Double valorConsulta, Double reputacion, ModelMap modelo) throws MiException {
//
//        List<Profesional> profesionales = pacienteServicio.buscarProfesionales(especialidad, valorConsulta, reputacion);
//        modelo.addAttribute("profesionales", profesionales);
//
//        return "inicio_paciente_2.html";
//    }
    
//    @PostMapping("/seleccionarProfesional/{id_profesional}")
//    public String profesionalE(@PathVariable String id_profesional, ModelMap modelo) {
//        Profesional profesional = pacienteServicio.seleccionarProfesional(id_profesional);
//
//        modelo.addAttribute("profesional", profesional);
//        return "inicio_paciente_2.html";
//
//    }
//
//    @GetMapping("/paciente/seleccionarProfesional/{idProfesional}")
//    public String seleccionarProfesional(@PathVariable String idProfesional, ModelMap model) {
//        Profesional profesionalSeleccionado = profesionalServicio.getOne(idProfesional);
//
//        model.addAttribute("profesionalSeleccionado", profesionalSeleccionado);
//
//        return "busqueda-por-especialidad.html";
//    }

}
