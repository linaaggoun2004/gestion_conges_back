package com.example.gestion_conges_back.DTO;

public class ProfilResponse {
  private String prenom;
  private String nom;
  private String dateNaissance;
  private String telephone;
  private String adresse;
  private String situationFamiliale;
  private String email;
  private String poste;
  private String dateEntree;
  private String service;
  private String manager;

  
  public ProfilResponse() {
}

    public ProfilResponse(String adresse, String dateEntree, String dateNaissance, String email, String manager, String nom, String poste, String prenom, String service, String situationFamiliale, String telephone) {
        this.adresse = adresse;
        this.dateEntree = dateEntree;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.manager = manager;
        this.nom = nom;
        this.poste = poste;
        this.prenom = prenom;
        this.service = service;
        this.situationFamiliale = situationFamiliale;
        this.telephone = telephone;
    }
  public String getPrenom() {
    return prenom;
  }
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }
  public String getNom() {
    return nom;
  }
  public void setNom(String nom) {
    this.nom = nom;
  }
  public String getDateNaissance() {
    return dateNaissance;
  }
  public void setDateNaissance(String dateNaissance) {
    this.dateNaissance = dateNaissance;
  }
  public String getTelephone() {
    return telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  public String getAdresse() {
    return adresse;
  }
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }
  public String getSituationFamiliale() {
    return situationFamiliale;
  }
  public void setSituationFamiliale(String situationFamiliale) {
    this.situationFamiliale = situationFamiliale;
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
  public String getDateEntree() {
    return dateEntree;
  }
  public void setDateEntree(String dateEntree) {
    this.dateEntree = dateEntree;
  }
  public String getService() {
    return service;
  }
  public void setService(String service) {
    this.service = service;
  }
  public String getManager() {
    return manager;
  }
  public void setManager(String manager) {
    this.manager = manager;
  }

    
}
