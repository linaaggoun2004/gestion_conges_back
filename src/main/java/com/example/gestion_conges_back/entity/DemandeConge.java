package com.example.gestion_conges_back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_conge")
@PrimaryKeyJoinColumn(name = "idD")

public class DemandeConge extends Demande {
    @Column(name = "type_conge")
    private String typeConge;
    @Column(name = "nbr_jours")
    private Double nbrJours;
    private String commentaire;

    public DemandeConge() {
        super();
    }

    public DemandeConge(String typeConge, Double nbrJours, String commentaire) {
        super();
        this.setTypeDemande(TypeDemandeEnum.CONGE);
        this.typeConge = typeConge;
        this.nbrJours = nbrJours;
        this.commentaire = commentaire;
    }

    public String getTypeConge() {
        return typeConge;
    }

    public void setTypeConge(String typeConge) {
        this.typeConge = typeConge;
    }

    public Double getNbrJours() {
        return nbrJours;
    }

    public void setNbrJours(Double nbrJours) {
        this.nbrJours = nbrJours;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
