package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion,Integer> {
}
