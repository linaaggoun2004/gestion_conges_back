package com.example.gestion_conges_back.DTO;

import com.example.gestion_conges_back.entity.RoleEnum;

public class EmployeListDto {
    private Long idE;
    private String nom;
    private String prenom;
    private String email;
    private String poste;
    private RoleEnum role;
    private Long serviceId;
    private String serviceNom;
    private Long managerId;
    private String managerNom;
    private Double soldeTotal;
    private Double soldeUtilise;
    private Double soldeRestant;

    public EmployeListDto() {
    }

    public EmployeListDto(String email, Long idE, Long managerId, String managerNom, String nom, String poste, String prenom, RoleEnum role, Long serviceId, String serviceNom, Double soldeRestant, Double soldeTotal, Double soldeUtilise) {
        this.email = email;
        this.idE = idE;
        this.managerId = managerId;
        this.managerNom = managerNom;
        this.nom = nom;
        this.poste = poste;
        this.prenom = prenom;
        this.role = role;
        this.serviceId = serviceId;
        this.serviceNom = serviceNom;
        this.soldeRestant = soldeRestant;
        this.soldeTotal = soldeTotal;
        this.soldeUtilise = soldeUtilise;
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
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public Long getServiceId() {
        return serviceId;
    }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public String getServiceNom() {
        return serviceNom;
    }
    public void setServiceNom(String serviceNom) {
        this.serviceNom = serviceNom;
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
    public Double getSoldeTotal() {
        return soldeTotal;
    }
    public void setSoldeTotal(Double soldeTotal) {
        this.soldeTotal = soldeTotal;
    }
    public Double getSoldeUtilise() {
        return soldeUtilise;
    }
    public void setSoldeUtilise(Double soldeUtilise) {
        this.soldeUtilise = soldeUtilise;
    }
    public Double getSoldeRestant() {
        return soldeRestant;
    }
    public void setSoldeRestant(Double soldeRestant) {
        this.soldeRestant = soldeRestant;
    }
    
}
