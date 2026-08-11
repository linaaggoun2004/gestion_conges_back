package com.example.gestion_conges_back.DTO;

import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.entity.TypeDemandeEnum;

public class DemandeManResponse {
    private Long idD;
    private String nomComplet;
    private TypeDemandeEnum type;
    private String typeConge;
    private String dateDebut;
    private String dateFin;
    private String nbjours;
    private String commentaire;
    private StatutEnum statut;
    private String dateCreation;
    private String poste;
    
    public DemandeManResponse(Long idD,String nomComplet, TypeDemandeEnum type, String typeConge, String dateDebut,
            String dateFin, String nbjours, String commentaire, StatutEnum statut,String dateCreation,String poste) {
        this.idD=idD;
        this.nomComplet = nomComplet;
        this.type = type;
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbjours = nbjours;
        this.commentaire = commentaire;
        this.statut = statut;
        this.dateCreation=dateCreation;
        this.poste=poste;
    }

    public Long getIdD() {
        return idD;
    }
    public void setIdD(Long idD) {
        this.idD = idD;
    }
    public String getNomComplet() {
        return nomComplet;
    }
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    public TypeDemandeEnum getType() {
        return type;
    }
    public void setType(TypeDemandeEnum type) {
        this.type = type;
    }
    public String getTypeConge() {
        return typeConge;
    }
    public void setTypeConge(String typeConge) {
        this.typeConge = typeConge;
    }
    public String getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }
    public String getDateFin() {
        return dateFin;
    }
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
    public String getNbjours() {
        return nbjours;
    }
    public void setNbjours(String nbjours) {
        this.nbjours = nbjours;
    }
    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public StatutEnum getStatut() {
        return statut;
    }
    public void setStatut(StatutEnum statut) {
        this.statut = statut;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
    
}
