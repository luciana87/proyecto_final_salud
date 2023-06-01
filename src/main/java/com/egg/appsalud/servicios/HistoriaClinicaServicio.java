/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.HistoriaClinica;
import com.egg.appsalud.entidades.NotaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.excepciones.MiException;
import com.egg.appsalud.repositorios.HistoriaClinicaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author carel
 */
@Service
public class HistoriaClinicaServicio {

    
    @Autowired
    HistoriaClinicaRepositorio historiaClinicaRepositorio;
    
    public HistoriaClinica getOne(Integer id_historiaClinica) {
        return historiaClinicaRepositorio.getOne(id_historiaClinica);
    }
    
    @Transactional
    public HistoriaClinica CrearHistoriaClinica(Paciente paciente, List<NotaMedica> lista) throws MiException{
        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setPaciente(paciente);
        historiaClinica.setNotas(lista);
        historiaClinicaRepositorio.save(historiaClinica);
        return historiaClinica;
    }
    public List<HistoriaClinica> listarHistoriaClinica() {

        List<HistoriaClinica> historiaClinica = historiaClinicaRepositorio.findAll();
        return historiaClinica;
    }
}