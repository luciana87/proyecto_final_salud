package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, Integer> {


    public List<Turno> BuscarPorPaciente(@Param("paciente") Paciente paciente);
    @Query("SELECT t FROM Turno t WHERE t.medico = :medico")
    public List<Turno>BuscarTurnosProfecional(@Param("medico") Profesional medico);
    
    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    public List<Turno>BuscarTurnosPaciente(@Param("paciente") Paciente paciente);

}
