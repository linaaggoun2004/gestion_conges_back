package com.example.gestion_conges_back.DTO;

public class EquipeMembreDto {
    private Long idE;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private Double soldeTotal;
    private Double soldeUtilise;
    private Double soldeRestant;
    private boolean present;
    private String typeAbsence;   
    private String finAbsence;    

    public Long getIdE() { return idE; }
    public void setIdE(Long idE) { this.idE = idE; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }
    public Double getSoldeTotal() { return soldeTotal; }
    public void setSoldeTotal(Double soldeTotal) { this.soldeTotal = soldeTotal; }
    public Double getSoldeUtilise() { return soldeUtilise; }
    public void setSoldeUtilise(Double soldeUtilise) { this.soldeUtilise = soldeUtilise; }
    public Double getSoldeRestant() { return soldeRestant; }
    public void setSoldeRestant(Double soldeRestant) { this.soldeRestant = soldeRestant; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
    public String getTypeAbsence() { return typeAbsence; }
    public void setTypeAbsence(String typeAbsence) { this.typeAbsence = typeAbsence; }
    public String getFinAbsence() { return finAbsence; }
    public void setFinAbsence(String finAbsence) { this.finAbsence = finAbsence; }
}
