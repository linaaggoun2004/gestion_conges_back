package com.example.gestion_conges_back.DTO;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long idN;
    private String contenu;
    private LocalDateTime dateEnvoi;
    private Boolean lu;

    public NotificationDto(Long idN, String contenu, LocalDateTime dateEnvoi, Boolean lu) {
        this.idN = idN;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.lu = lu;
    }

    public Long getIdN() { return idN; }
    public String getContenu() { return contenu; }
    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public Boolean getLu() { return lu; }
}
