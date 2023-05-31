package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.mail = :mail")
    public Profesional BuscarPorEmail(@Param("mail") String mail);
    
    
        @Query("SELECT p FROM Profesional p WHERE p.especialidad = :pediatra")
    public Profesional BuscarPorEspecialidadPediatra(@Param("pediatra") String pediatria);

      @Query("SELECT p FROM Profesional p WHERE p.especialidad = :ginecologia")
    public Profesional BuscarPorEspecialidadGinecologia(@Param("ginecologia") String ginecologia);
    
    
      @Query("SELECT p FROM Profesional p WHERE p.especialidad = :clinica")
    public Profesional BuscarPorEspecialidadClinica(@Param("clinica") String clinica);
    
      @Query("SELECT p FROM Profesional p WHERE p.especialidad = :pediatra")
    public Profesional BuscarPorEspecialidadCardiologia(@Param("cardiologia") String cardiologia);
    
    
}
