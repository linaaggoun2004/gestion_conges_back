package com.example.gestion_conges_back.DTO;

public class DemandeCongeRequest {
    private String typeConge;
    private String dateDebut;
    private String dateFin;
    private String commentaire;

    public DemandeCongeRequest(String typeConge, String dateDebut, String dateFin,
            String commentaire) {
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
    }

    public DemandeCongeRequest() {
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
