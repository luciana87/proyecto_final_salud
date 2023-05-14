package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/registrar") //Retorna vista para registrarse
    public String registrar(){
        return "paciente_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "lista_paciente.html"; //Retorna vista con todos los pacientes persistidos en la DB (tabla, o card de pacientes)
    }


    @GetMapping("/obtener/{idPaciente}")
    public String obtenerPorId (@PathVariable String idPaciente, ModelMap modelo) {
        try {

            Paciente paciente = pacienteServicio.buscarPorId(idPaciente);
            modelo.put("paciente", paciente);//No deberia mostrasr usuario y contraseña
            return "datos_paciente.html";

        } catch (MiException e){

            modelo.put("error", e.getMessage());
            return "index.html";//Retorna vista a definir

        }
    }
}
