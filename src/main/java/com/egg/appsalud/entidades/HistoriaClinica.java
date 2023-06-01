package com.egg.appsalud.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne (mappedBy = "historiaClinica")
    private Paciente paciente;

    @OneToMany (mappedBy = "historiaClinica")
    private List<NotaMedica> notaM;


    public HistoriaClinica(Integer id) {
        this.id = id;
    }
    public HistoriaClinica() {
    }

    public HistoriaClinica(Integer id, Paciente paciente, List<NotaMedica> notaM) {
        this.id = id;
        this.paciente = paciente;
        this.notaM = notaM;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<NotaMedica> getNotaM() {
        return notaM;
    }

    public void setNotaM(List<NotaMedica> notas) {
        this.notaM = notas;
    }
    
    
}
