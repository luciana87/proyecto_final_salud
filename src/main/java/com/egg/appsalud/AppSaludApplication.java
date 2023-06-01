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
public class AppSaludApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(AppSaludApplication.class, args);
    }    

    @Autowired
    ProfesionalRepositorio profRepo;
    @Autowired
    TurnoServicio turnoServicio;
    @Autowired
    JornadaLaboralRepositorio jornadaLaboralRepositorio;
   
    @Override
    public void run(String... args) throws Exception {
        
        Profesional profesional = profRepo.BuscarPorEmail("rivas@prueba.com");
        JornadaLaboral jornada = new JornadaLaboral(profesional,"MONDAY", LocalTime.parse("06:00"), LocalTime.parse("11:00"),45l);
        jornadaLaboralRepositorio.save(jornada);
        
        LocalDate inicioFecha = LocalDate.of(2023, 5, 1);
        LocalDate finFecha = LocalDate.of(2023, 5, 31);
        
        
        turnoServicio.crearTurno(profesional.getId(),inicioFecha,finFecha);
        
        
    }
}


