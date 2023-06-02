package com.egg.appsalud.repositorios;

<<<<<<< HEAD
=======
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
>>>>>>> develop
import com.egg.appsalud.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

<<<<<<< HEAD
    public Turno getOne(Integer idTurno);
    
    
=======


    @Query("SELECT t FROM Turno t WHERE t.medico = :medico")
    public List<Turno>BuscarTurnosProfecional(@Param("medico") Profesional medico);
    
    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    public List<Turno>BuscarTurnosPaciente(@Param("paciente") Paciente paciente);

>>>>>>> develop
}
