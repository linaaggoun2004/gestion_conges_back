package com.example.gestion_conges_back.entity;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idV;
    @Column(name = "date_decision")
    private LocalDateTime dateDecision;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private DecEnum decision = DecEnum.EN_ATTENTE;

    private String niveau;

    private String commentaire;

    // Agregation
    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    // Association
    @ManyToOne
    @JoinColumn(name = "validateur_id", nullable = false)
    private Employe validateur;
}
