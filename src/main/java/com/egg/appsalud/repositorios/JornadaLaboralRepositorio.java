package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.JornadaLaboral;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaLaboralRepositorio extends JpaRepository<JornadaLaboral, String>{

    @Query("SELECT e FROM JornadaLaboral e WHERE e.profesional.id = :id")
    public List<JornadaLaboral> jornadasL(@Param("id") String id);
    
    @Query("SELECT e FROM JornadaLaboral e WHERE e.horaInicio = :horaInicio")
    public List<JornadaLaboral> buscarPorHoraInicio(@Param("horaInicio") LocalTime horaInicio);
}
