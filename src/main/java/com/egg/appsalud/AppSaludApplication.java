package com.egg.appsalud;


import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.repositorios.JornadaLaboralRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.servicios.JornadaLaboralServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.TurnoServicio;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSaludApplication{
    public static void main(String[] args) {
        SpringApplication.run(AppSaludApplication.class, args);
    }    
  
}


