package com.example.gestion_conges_back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "splde_conge", uniqueConstraints = @UniqueConstraint(columnNames = { "employe_id", "annee" }))
public class SoldeConge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idC;

    @Column(name = "total_jours")
    private Double totalJours = 0.0;
    @Column(name = "Jours_utilises")
    private Double JoursUtilises = 0.0;
    @Column(name = "Jours_restantes")
    private Double JoursRestantes = 0.0;
    private Integer annee;
    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    public SoldeConge(Long idC, Double totalJours, Double joursUtilises, Double joursRestantes, Integer annee,
            Employe employe) {
        this.idC = idC;
        this.totalJours = totalJours;
        JoursUtilises = joursUtilises;
        JoursRestantes = joursRestantes;
        this.annee = annee;
        this.employe = employe;
    }

    public SoldeConge() {
    }

    public Long getIdC() {
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public Double getTotalJours() {
        return totalJours;
    }

    public void setTotalJours(Double totalJours) {
        this.totalJours = totalJours;
    }

    public Double getJoursUtilises() {
        return JoursUtilises;
    }

    public void setJoursUtilises(Double joursUtilises) {
        JoursUtilises = joursUtilises;
    }

    public Double getJoursRestantes() {
        return JoursRestantes;
    }

    public void setJoursRestantes(Double joursRestantes) {
        JoursRestantes = joursRestantes;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

}
