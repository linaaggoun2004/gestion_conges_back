package com.example.gestion_conges_back.DTO;

public class Servicedto {
    private Long idS;
    private String nom;
    private String description;

    public Servicedto(Long idS, String nom, String description) {
        this.idS = idS;
        this.nom = nom;
        this.description = description;
    }

    public Servicedto() {
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
