/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author carel
 */
@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, Integer> {

    @Query("SELECT a FROM HistoriaClinica a WHERE a.paciente = :paciente")
    public HistoriaClinica buscarPaciente(@Param("paciente") Paciente paciente);

}
