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
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoc;

    @Column(name = "nom_fichier")
    private String nomFichier;
    @Column(name = "duree_conservation")
    private Integer dureeConservation;
    private String chemin;
    @Column(name = "date_depot")
    private LocalDateTime dateDepot = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "demande_arret_maladie_id", nullable = false)
    private DemandeArretMaladie demandeArretMaladie;

    public Document() {
    }

    public Document(Long idDoc, String nomFichier, Integer dureeConservation, String chemin, LocalDateTime dateDepot,
            DemandeArretMaladie demandeArretMaladie) {
        this.idDoc = idDoc;
        this.nomFichier = nomFichier;
        this.dureeConservation = dureeConservation;
        this.chemin = chemin;
        this.dateDepot = dateDepot;
        this.demandeArretMaladie = demandeArretMaladie;
    }

    public Long getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(Long idDoc) {
        this.idDoc = idDoc;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Integer getDureeConservation() {
        return dureeConservation;
    }

    public void setDureeConservation(Integer dureeConservation) {
        this.dureeConservation = dureeConservation;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public DemandeArretMaladie getDemandeArretMaladie() {
        return demandeArretMaladie;
    }

    public void setDemandeArretMaladie(DemandeArretMaladie demandeArretMaladie) {
        this.demandeArretMaladie = demandeArretMaladie;
    }

}
