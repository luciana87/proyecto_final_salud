/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.servicios.HistoriaClinicaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author carel
 */

@Controller
@RequestMapping("/historia-clinica")
public class HistoriaClinicaControlador {

        @Autowired
        private HistoriaClinicaServicio historiaClinicaServicio;

        @GetMapping("/lista")
        public String listar(ModelMap modelo) {
            List<HistoriaClinica> historiaClinica = historiaClinicaServicio.listarHistoriaClinica();
            modelo.addAttribute("historiaClinica", historiaClinica);

            return "lista-historia-clinica.html"; 
        }

    
}
