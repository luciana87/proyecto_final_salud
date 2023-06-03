package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.JornadaLaboral;
import com.egg.appsalud.entidades.Profesional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.mail = :mail")
    public Profesional BuscarPorEmail(@Param("mail") String mail);

    @Query("SELECT e FROM Profesional e WHERE e.especialidad LIKE :especialidad")
    public List<Profesional> buscarPorEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT p.jornadaLaboral FROM Profesional p WHERE p.id = :id")
    public List<JornadaLaboral> buscarJornadasLaboralesPorId(@Param("id") String id);

}
