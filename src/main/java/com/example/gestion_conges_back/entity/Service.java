package com.example.gestion_conges_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idS;

    private String nom;
    private String description;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employe manager;

    
    public Service(Long idS, String nom, String description) {
        this.idS = idS;
        this.nom = nom;
        this.description = description;
    }

    
    public Service() {
    }

    public Service(String description, Long idS, Employe manager, String nom) {
        this.description = description;
        this.idS = idS;
        this.manager = manager;
        this.nom = nom;
    }


    public Long getIdS() {
        return idS;
    }
    public void setIdS(Long idS) {
        this.idS = idS;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Employe getManager() {
        return manager;
    }

    public void setManager(Employe manager) {
        this.manager = manager;
    }

    
    

}
