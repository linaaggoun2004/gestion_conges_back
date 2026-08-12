package com.example.gestion_conges_back.DTO;

import com.example.gestion_conges_back.entity.RoleEnum;

public class RolseResponse {
    private Long idE;
    private String nom;
    private String prenom;
    private String service;
    private String poste;
    private RoleEnum role;

    
    public RolseResponse(Long idE, String nom, String prenom, String service, String poste, RoleEnum role) {
        this.idE = idE;
        this.nom = nom;
        this.prenom = prenom;
        this.service = service;
        this.poste = poste;
        this.role = role;
    }
    public Long getIdE() {
        return idE;
    }
    public void setIdE(Long idE) {
        this.idE = idE;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return service;
    }
    public void setEmail(String email) {
        this.service = email;
    }
    public String getPoste() {
        return poste;
    }
    public void setPoste(String poste) {
        this.poste = poste;
    }
    public RoleEnum getRole() {
        return role;
    }
    public void setRole(RoleEnum role) {
        this.role = role;
    }
    
}
