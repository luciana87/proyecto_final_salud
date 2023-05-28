/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;


import com.egg.appsalud.Enumerativos.Rol;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author franc
 */
@MappedSuperclass
public class Usuario {

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 40)
    private String dni;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    
    private Rol rol;

    @Column(nullable = false)
    private String telefono;
    

    public Usuario() {
    }


    public Usuario(String mail, String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento, String telefono) {

        this.mail = mail;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
        this.telefono = telefono;
        this.rol = rol;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
