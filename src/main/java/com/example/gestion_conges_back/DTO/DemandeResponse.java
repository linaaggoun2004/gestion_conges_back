package com.example.gestion_conges_back.DTO;

import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.entity.TypeDemandeEnum;

public class DemandeResponse {
    private Long idD;
    private TypeDemandeEnum type;
    private String typeConges;
    private String dateDebut;
    private String dateFin;
    private Double Nbjour;
    private String Note;
    private String dateCreationbut;
    private StatutEnum statut;
    public DemandeResponse() { }


    public DemandeResponse(Double Nbjour, String Note, String dateCreation, String dateDebut, String dateFin, Long idD, StatutEnum statut, TypeDemandeEnum type, String typeConges) {
        this.Nbjour = Nbjour;
        this.Note = Note;
        this.dateCreationbut = dateCreation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idD = idD;
        this.statut = statut;
        this.type = type;
        this.typeConges = typeConges;
    }
    public Long getIdD() {
        return idD;
    }
    public void setIdD(Long idD) {
        this.idD = idD;
    }
    public TypeDemandeEnum getType() {
        return type;
    }
    public void setType(TypeDemandeEnum type) {
        this.type = type;
    }
    public String getTypeConges() {
        return typeConges;
    }
    public void setTypeConges(String typeConges) {
        this.typeConges = typeConges;
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
    public Double getNbjour() {
        return Nbjour;
    }
    public void setNbjour(Double nbjour) {
        Nbjour = nbjour;
    }
    public String getNote() {
        return Note;
    }
    public void setNote(String note) {
        Note = note;
    }
    public String getDateCreationbut() {
        return dateCreationbut;
    }
    public void setDateCreationbut(String dateCreationbut) {
        this.dateCreationbut = dateCreationbut;
    }
    public StatutEnum getStatut() {
        return statut;
    }
    public void setStatut(StatutEnum statut) {
        this.statut = statut;
    }
    

    
}
