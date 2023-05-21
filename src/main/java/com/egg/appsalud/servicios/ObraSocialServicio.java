/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ObraSocialRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author franc
 */
@Service
public class ObraSocialServicio {
    @Autowired
    private ObraSocialRepositorio obraSocialRepositorio;
    
    
    @Transactional
    public void CrearObraSocial(String nombre) throws MiException{
        validar(nombre);
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setNombre(nombre);
        obraSocialRepositorio.save(obraSocial);
        
    }
    
    @Transactional
    public void EliminarObraSocial(Integer id){
        
        obraSocialRepositorio.deleteById(id);
        
        
    }
    
    public List<ObraSocial> ListarObraSocial(){
        
        List<ObraSocial>listaObraSocial = new ArrayList();
        
        listaObraSocial = obraSocialRepositorio.findAll();
        
        return listaObraSocial;
        
        
    }
    
    
    
    private void validar(String nombre) throws MiException{
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El Nombre no puede ser nulo o estar vacio");
        }
    }
}
