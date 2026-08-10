package com.example.gestion_conges_back.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idD;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type_demande")
    private TypeDemandeEnum typeDemande;

    @Column(name = "date_debut")
    private LocalDate dateDebut;
    @Column(name = "date_fin")
    private LocalDate dateFin;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private StatutEnum statut = StatutEnum.BROUILLON;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    public Demande() {
    }

    public Demande(Long idD, TypeDemandeEnum typeDemande, LocalDate dateDebut, LocalDate dateFin,
            LocalDateTime dateCreation, StatutEnum statut, Employe employe) {
        this.idD = idD;
        this.typeDemande = typeDemande;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.employe = employe;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }

    public TypeDemandeEnum getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemandeEnum typeDemande) {
        this.typeDemande = typeDemande;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutEnum getStatut() {
        return statut;
    }

    public void setStatut(StatutEnum statut) {
        this.statut = statut;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

}
