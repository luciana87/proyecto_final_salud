/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.entidades.NotaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author carel
 */
@Repository
public interface NotaMedicaRepositorio extends JpaRepository<NotaMedica, Integer> {

    @Query("SELECT n FROM NotaMedica n WHERE n.historiaClinica = :historiaClinica")
    public List<NotaMedica>buscarPorHistoria(@Param("historiaClinica") HistoriaClinica historiaClinica);
}
