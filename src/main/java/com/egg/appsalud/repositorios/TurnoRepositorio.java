package com.egg.appsalud.repositorios;

import com.egg.appsalud.Enumerativos.EstadoTurno;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, Integer> {



    @Query("SELECT t FROM Turno t WHERE t.medico = :medico")
    public List<Turno>BuscarTurnosProfecional(@Param("medico") Profesional medico);
    
    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    public List<Turno>BuscarTurnosPaciente(@Param("paciente") Paciente paciente);
    

    @Query("SELECT t FROM Turno t INNER JOIN t.medico p " +
       "WHERE (:fecha IS NULL OR t.fecha = :fecha) " +
            "AND (:estado IS NULL OR t.estado = :estado) " +
       "AND (:horario IS NULL OR t.horario = :horario) " +
       "AND (:nombre IS NULL OR p.nombre = :nombre) " +
//       "AND (:especialidad IS NULL OR p.especialidad = :especialidad) " +
//     "AND (:reputacion IS NULL OR p.reputacion = :reputacion)" +        
       "AND (:valorConsulta IS NULL OR p.valorConsulta = :valorConsulta) "  +
       "AND (:medico IS NULL OR p = :medico)")
    List<Turno> buscarTurnosFiltro(@Param("medico") Profesional medico, @Param("fecha") LocalDate fecha, @Param("horario") LocalTime horario, @Param("nombre") String nombre, @Param("valorConsulta") Double valorConsulta, @Param("estado") EstadoTurno estado);

}
