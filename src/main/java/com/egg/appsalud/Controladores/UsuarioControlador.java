/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author franc
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/iniciar-Session")
    public String iniciarSession() {
        return null;
    }

    // ------------------------------------------ REESTABLECER CONTRASEÑA ------------------------------------------
    @GetMapping("/reestablecerContrasenia")
    public String reestablecerContra() {
        return "login-reestablecer-contrasenia.html";
    }

    @PostMapping("/reestableciendoContrasenia")
    public String reestableciendoContra(@RequestParam("mail") String mail, ModelMap modelo) throws MiException {

//        usuarioServicio.solicitarContra(mail);
//
//        return "redirect:/inicio";
        try {
            usuarioServicio.solicitarContra(mail);
            modelo.put("exito", "Contraseña reestablecida");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "reestablecer.html";
        }

        return "redirect:/inicio";
    }

}
