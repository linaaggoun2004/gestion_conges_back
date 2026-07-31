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
}
