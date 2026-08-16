package com.example.gestion_conges_back.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.EquipeMembreDto;
import com.example.gestion_conges_back.entity.DemandeArretMaladie;
import com.example.gestion_conges_back.entity.DemandeConge;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.SoldeConge;
import com.example.gestion_conges_back.entity.StatutEnum;
import com.example.gestion_conges_back.repository.DemandeArretMaladierepository;
import com.example.gestion_conges_back.repository.DemandeCongerepository;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.repository.SoldeCongerepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipeService {
   private final Employerepository emprep;
    private final SoldeCongerepository soldeRepository;
    private final DemandeCongerepository demandeCongeRepository;         // ajustez le nom si différent
    private final DemandeArretMaladierepository demandeArretRepository;  // ajustez le nom si différent
    private final WorkflowService workflowService;

    public List<EquipeMembreDto> getEquipeDuManager(Long managerId) {
        Employe manager = emprep.findById(managerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager introuvable"));

        int annee = LocalDate.now().getYear();
        LocalDate aujourdhui = LocalDate.now();

        List<Employe> membres = emprep.findAll().stream()
                .filter(e -> !e.getIdE().equals(managerId))
                .filter(e -> workflowService.estManagerDe(manager, e))
                .collect(Collectors.toList());

        return membres.stream().map(e -> {
            EquipeMembreDto dto = new EquipeMembreDto();
            dto.setIdE(e.getIdE());
            dto.setNom(e.getNom());
            dto.setPrenom(e.getPrenom());
            dto.setEmail(e.getUsername());
            dto.setTelephone(e.getTelephone());
            dto.setPoste(e.getPoste());

            SoldeConge solde = soldeRepository.findByEmployeIdEAndAnnee(e.getIdE(), annee).orElse(null);
            dto.setSoldeTotal(solde != null ? solde.getTotalJours() : 0.0);
            dto.setSoldeUtilise(solde != null ? solde.getJoursUtilises() : 0.0);
            dto.setSoldeRestant(solde != null ? solde.getJoursRestantes() : 0.0);

            // ---- Statut aujourd'hui : congé approuvé/validé couvrant la date du jour ----
            DemandeConge congeEnCours = demandeCongeRepository.findAll().stream()
                    .filter(d -> d.getEmploye().getIdE().equals(e.getIdE()))
                    .filter(d -> d.getStatut() == StatutEnum.APPROUVEE || d.getStatut() == StatutEnum.VALIDEE_MANAGER)
                    .filter(d -> !aujourdhui.isBefore(d.getDateDebut()) && !aujourdhui.isAfter(d.getDateFin()))
                    .findFirst().orElse(null);

            DemandeArretMaladie arretEnCours = congeEnCours == null
                    ? demandeArretRepository.findAll().stream()
                        .filter(d -> d.getEmploye().getIdE().equals(e.getIdE()))
                        .filter(d -> d.getStatut() == StatutEnum.APPROUVEE || d.getStatut() == StatutEnum.VALIDEE_MANAGER)
                        .filter(d -> !aujourdhui.isBefore(d.getDateDebut()) && !aujourdhui.isAfter(d.getDateFin()))
                        .findFirst().orElse(null)
                    : null;

            if (congeEnCours != null) {
                dto.setPresent(false);
                dto.setTypeAbsence(congeEnCours.getTypeConge());
                dto.setFinAbsence(congeEnCours.getDateFin().toString());
            } else if (arretEnCours != null) {
                dto.setPresent(false);
                dto.setTypeAbsence("Arrêt maladie");
                dto.setFinAbsence(arretEnCours.getDateFin().toString());
            } else {
                dto.setPresent(true);
            }

            return dto;
        }).collect(Collectors.toList());
    } 
}
