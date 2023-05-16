package com.egg.appsalud;



import com.egg.appsalud.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSaludApplication implements CommandLineRunner{

	public static void main(String[] args) {
            SpringApplication.run(AppSaludApplication.class, args);       
	}

    @Autowired
    private ServicioPaciente servPaciente;
    @Override
    public void run(String... args) throws Exception {
        
        
                
        servPaciente.CrearPaciente("prueba@gmail.com", "12345", "Francisco", "Nuñez", "1234567", 23,1532442222);
    }

}
