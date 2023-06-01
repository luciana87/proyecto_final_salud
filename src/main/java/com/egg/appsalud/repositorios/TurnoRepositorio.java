package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, Integer> {



    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    public List<Turno> BuscarPorPaciente(@Param("paciente") Paciente paciente);
}
