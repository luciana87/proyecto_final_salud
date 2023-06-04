/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.ObraSocialRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author franc
 */
@Service
public class ObraSocialServicio {
    @Autowired
    private ObraSocialRepositorio obraSocialRepositorio;


    @Transactional
    public void CrearObraSocial(String nombre) throws MiException {
        validar(nombre);
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setNombre(nombre);
        obraSocialRepositorio.save(obraSocial);

    }

    @Transactional
    public void EliminarObraSocial(Integer id) {

        obraSocialRepositorio.deleteById(id);
    }

    public List<ObraSocial> listarObraSocial() {

        List<ObraSocial> listaObraSocial = new ArrayList();

        listaObraSocial = obraSocialRepositorio.findAll();

        return listaObraSocial;


    }

    @Transactional
    public void crearObraSocial(String nombre) throws MiException {

        validar(nombre);

        ObraSocial obraSocial = new ObraSocial();

        obraSocial.setNombre(nombre);

        obraSocialRepositorio.save(obraSocial);
    }

    public ObraSocial getOne(Integer id) throws MiException {
        return obraSocialRepositorio.getOne(id);
    }

    @Transactional
    public void modificarObraSocial(Integer id, String nombre) throws MiException {

        validar(nombre);

        Optional<ObraSocial> obraSocialOptional = obraSocialRepositorio.findById(id);

        if (obraSocialOptional.isPresent()) {
            ObraSocial obraSocial = obraSocialOptional.get();

            obraSocial.setNombre(nombre);

            obraSocialRepositorio.save(obraSocial);

        }
    }
    
    private void validar(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo o estar vacio");
        }
    }
}


