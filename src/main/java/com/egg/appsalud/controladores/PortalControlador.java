
package com.egg.appsalud.controladores;

import com.egg.appsalud.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
}
