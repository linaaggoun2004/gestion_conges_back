package com.example.gestion_conges_back.DTO;

public class Servicedto {
    private Long idS;
    private String nom;
    private String description;

     private Long managerId;
    private String managerNom;
    private String managerPrenom;
    private Integer nbEmployes;

    

    public Servicedto(Long idS, String nom, String description, Long managerId, String managerNom, String managerPrenom,
            Integer nbEmployes) {
        this.idS = idS;
        this.nom = nom;
        this.description = description;
        this.managerId = managerId;
        this.managerNom = managerNom;
        this.managerPrenom = managerPrenom;
        this.nbEmployes = nbEmployes;
    }
    public Servicedto(Long idS, String nom, String description) {
        this.idS = idS;
        this.nom = nom;
        this.description = description;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerNom() {
        return managerNom;
    }

    public void setManagerNom(String managerNom) {
        this.managerNom = managerNom;
    }

    public String getManagerPrenom() {
        return managerPrenom;
    }

    public void setManagerPrenom(String managerPrenom) {
        this.managerPrenom = managerPrenom;
    }

    public Integer getNbEmployes() {
        return nbEmployes;
    }

    public void setNbEmployes(Integer nbEmployes) {
        this.nbEmployes = nbEmployes;
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
