package com.egg.appsalud.repositorios;


import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.entidades.JornadaLaboral;
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
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.mail = :mail")
    public Profesional BuscarPorEmail(@Param("mail") String mail);

    @Query("SELECT p FROM Profesional p WHERE (:especialidad IS NULL OR p.especialidad = :especialidad) AND (:valorConsulta IS NULL OR p.valorConsulta <= :valorConsulta) AND (:reputacion IS NULL OR p.reputacion = :reputacion)")
    public List<Profesional> buscarPorEspecialidadValorConsultaReputacion(@Param("especialidad") Especialidad especialidad, @Param("valorConsulta") Double valorConsulta, @Param("reputacion") Double reputacion);

    @Query("SELECT p.jornadaLaboral FROM Profesional p WHERE p.id = :id")
    public List<JornadaLaboral> buscarJornadasLaboralesPorId(@Param("id") String id);

    
    
}
