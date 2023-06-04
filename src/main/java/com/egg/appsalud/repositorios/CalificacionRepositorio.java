package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Calificacion;
import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion,Integer> {

    @Query("SELECT c FROM Calificacion c WHERE c.medico = :medico")
    public List<Calificacion> calificacionesSegunProfesional(@Param("medico") Profesional profesional);
}
