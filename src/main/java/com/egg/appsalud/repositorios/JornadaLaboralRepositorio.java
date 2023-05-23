package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaLaboralRepositorio extends JpaRepository<JornadaLaboral, String>{
    
}
