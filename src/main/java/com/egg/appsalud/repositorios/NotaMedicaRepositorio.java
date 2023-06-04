/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.NotaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author carel
 */
@Repository
public interface NotaMedicaRepositorio extends JpaRepository<NotaMedica, Integer> {
    
}
