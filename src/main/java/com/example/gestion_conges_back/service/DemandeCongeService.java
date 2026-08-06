package com.example.gestion_conges_back.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.DemandeCongeRequest;
import com.example.gestion_conges_back.entity.DemandeConge;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.entity.TypeDemandeEnum;
import com.example.gestion_conges_back.repository.DemandeCongerepository;
import com.example.gestion_conges_back.repository.Employerepository;

@Service
public class DemandeCongeService {
    private final DemandeCongerepository demandeCongRep;
    private final Employerepository employeRep;

    public DemandeCongeService(DemandeCongerepository demandeCongRep, Employerepository employeRep) {
        this.demandeCongRep = demandeCongRep;
        this.employeRep = employeRep;
    }

    public DemandeConge creerDemandeConge(DemandeCongeRequest request, Long empId) {

        Employe emp = this.employeRep.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        DemandeConge demande = new DemandeConge();

        demande.setTypeDemande(TypeDemandeEnum.CONGE);
        demande.setDateDebut(LocalDate.parse(request.getDateDebut()));
        demande.setDateFin(LocalDate.parse(request.getDateFin()));
        demande.setDateCreation(LocalDateTime.now());
        demande.setEmploye(emp);
        demande.setStatut(StatutEnum.EN_ATTENTE_MANAGER);
        demande.setTypeConge(request.getTypeConge());
        demande.setCommentaire(request.getCommentaire());

        double nbr_jours = ChronoUnit.DAYS.between(LocalDate.parse(request.getDateDebut()),
                LocalDate.parse(request.getDateFin())) + 1;
        demande.setNbrJours(nbr_jours);

        return demandeCongRep.save(demande);

    }

    public List<DemandeConge> getAllDemandeCongeIdE(Long employeId) {
        return demandeCongRep.findByEmployeIdE(employeId);
    }

    public List<DemandeConge> getAllDemandeConge() {
        return demandeCongRep.findAll();
    }

    public DemandeConge annulerDemandeConge(Long id) {
        DemandeConge demande = demandeCongRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatut(StatutEnum.ANNULEE);

        return demandeCongRep.save(demande);
    }

    public DemandeConge getDemandeParId(Long id) {
        return demandeCongRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Demande introuvable avec l'id : " + id));
    }

}
