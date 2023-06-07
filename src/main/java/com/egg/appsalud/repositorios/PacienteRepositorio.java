/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author franc
 */
@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente,String>{
    
    @Query("SELECT p FROM Paciente p WHERE p.mail = :mail")
    public Paciente BuscarPorEmail(@Param("mail") String mail);
    
}
