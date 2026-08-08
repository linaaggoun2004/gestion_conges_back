package com.example.gestion_conges_back.DTO;

public class IndicateurResponse {
    
    private long totalEmp;
    private long demandesEnAttentes;

    
    public IndicateurResponse() {
    }
    public IndicateurResponse(long totalEmp, long demandesEnAttentes) {
        this.totalEmp = totalEmp;
        this.demandesEnAttentes = demandesEnAttentes;
    }
    public long getTotalEmp() {
        return totalEmp;
    }
    public void setTotalEmp(long totalEmp) {
        this.totalEmp = totalEmp;
    }
    public long getDemandesEnAttentes() {
        return demandesEnAttentes;
    }
    public void setDemandesEnAttentes(long demandesEnAttentes) {
        this.demandesEnAttentes = demandesEnAttentes;
    }
    
}
