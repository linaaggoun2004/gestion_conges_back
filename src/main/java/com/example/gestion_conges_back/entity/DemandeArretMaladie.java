package com.example.gestion_conges_back.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_arret_maladie")
@PrimaryKeyJoinColumn(name = "idD")
public class DemandeArretMaladie extends Demande {
    private Integer duree;
    private String metadonnees;
    @OneToMany(mappedBy = "demandeArretMaladie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    public DemandeArretMaladie() {
        super();
    }

    public DemandeArretMaladie(Integer duree, String metadonnees) {
        super();
        this.setTypeDemande(TypeDemandeEnum.ARRET_MALADIE);
        this.duree = duree;
        this.metadonnees = metadonnees;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getMetadonnees() {
        return metadonnees;
    }

    public void setMetadonnees(String metadonnees) {
        this.metadonnees = metadonnees;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

}
