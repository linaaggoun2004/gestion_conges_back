package com.example.gestion_conges_back.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idN;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;
    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi = LocalDateTime.now();

    private Boolean lu = false;

    // Destinataire de la notification
    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    // Evenement declencheur (nullable : certaines notifications peuvent
    // ne pas provenir d'une validation, ex: rappel de solde)
    @ManyToOne
    @JoinColumn(name = "validation_id")
    private Validation validation;

    public Notification() {
    }

    public Notification(Long idN, String contenu, LocalDateTime dateEnvoi, Boolean lu, Employe employe,
            Validation validation) {
        this.idN = idN;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.lu = lu;
        this.employe = employe;
        this.validation = validation;
    }

    public Long getIdN() {
        return idN;
    }

    public void setIdN(Long idN) {
        this.idN = idN;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Boolean getLu() {
        return lu;
    }

    public void setLu(Boolean lu) {
        this.lu = lu;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    
}
