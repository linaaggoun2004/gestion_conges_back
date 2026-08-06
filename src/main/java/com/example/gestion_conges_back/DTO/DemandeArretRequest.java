package com.example.gestion_conges_back.DTO;

public class DemandeArretRequest {

    private String dateDebut;
    private String dateFin;
    private String metadonnes;

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

    public String getMetadonnes() {
        return metadonnes;
    }

    public void setMetadonnes(String metadonnes) {
        this.metadonnes = metadonnes;
    }

    public DemandeArretRequest(String dateDebut, String dateFin, String metadonnes) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.metadonnes = metadonnes;
    }

    public DemandeArretRequest() {
    }

}
