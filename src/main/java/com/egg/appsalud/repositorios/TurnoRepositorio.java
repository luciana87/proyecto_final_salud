package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Turno;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    public Optional<Turno> findById(int id);

}
