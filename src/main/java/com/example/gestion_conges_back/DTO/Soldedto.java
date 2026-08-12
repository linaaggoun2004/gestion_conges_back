package com.example.gestion_conges_back.DTO;


public class Soldedto {
    private Double totalJours ;
    private Double JoursUtilises ;
    private Double JoursRestantes ;
    
    public Soldedto() {
    }
    public Soldedto(Double totalJours, Double joursUtilises, Double joursRestantes) {
        this.totalJours = totalJours;
        JoursUtilises = joursUtilises;
        JoursRestantes = joursRestantes;
    }
    public Double getTotalJours() {
        return totalJours;
    }
    public void setTotalJours(Double totalJours) {
        this.totalJours = totalJours;
    }
    public Double getJoursUtilises() {
        return JoursUtilises;
    }
    public void setJoursUtilises(Double joursUtilises) {
        JoursUtilises = joursUtilises;
    }
    public Double getJoursRestantes() {
        return JoursRestantes;
    }
    public void setJoursRestantes(Double joursRestantes) {
        JoursRestantes = joursRestantes;
    }

    
}
