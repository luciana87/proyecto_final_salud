/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author franc
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    
    @PostMapping("/iniciar-Session")
    public String iniciarSession(){
        return null;
    }
            
    
    
}
