/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author franc
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    
}
