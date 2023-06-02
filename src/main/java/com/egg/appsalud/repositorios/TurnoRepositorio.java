package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    public Turno getOne(Integer idTurno);
    
    
}
