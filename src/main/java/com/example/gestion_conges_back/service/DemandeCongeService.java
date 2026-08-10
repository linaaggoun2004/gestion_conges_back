package com.example.gestion_conges_back.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.DemandeCongeRequest;
import com.example.gestion_conges_back.DTO.DemandeResponse;
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

    public String creerDemandeConge(DemandeCongeRequest request, Long empId) {

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

        demandeCongRep.save(demande);
        return "la demande a bien ete creer";

    }

    public List<DemandeResponse> getAllDemandeCongeIdE(Long employeId) {


        List<DemandeConge> demandes=demandeCongRep.findByEmployeIdE(employeId);
        List<DemandeResponse> responses = new ArrayList<>();

        for ( DemandeConge demande : demandes){
            DemandeResponse response= new DemandeResponse(demande.getNbrJours(),
                demande.getCommentaire(),
                demande.getDateCreation().toString(),
                demande.getDateDebut().toString(),
                demande.getDateFin().toString(),
                demande.getIdD(),
                demande.getStatut(),
                TypeDemandeEnum.CONGE,
                demande.getTypeConge()
            );
            responses.add(response);

        }
        return responses;
    }

    public List<DemandeConge> getAllDemandeConge() {
        return demandeCongRep.findAll();
    }

    public String annulerDemandeConge(Long id) {
        DemandeConge demande = demandeCongRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatut(StatutEnum.ANNULEE);

        demandeCongRep.save(demande);
        return "La demande est annulee avec succes";
    }

    public DemandeConge getDemandeParId(Long id) {
        return demandeCongRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Demande introuvable avec l'id : " + id));
    }

}
