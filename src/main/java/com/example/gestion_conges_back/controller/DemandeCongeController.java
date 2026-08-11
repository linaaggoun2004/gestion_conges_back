package com.example.gestion_conges_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.DemandeCongeRequest;
import com.example.gestion_conges_back.DTO.DemandeManResponse;
import com.example.gestion_conges_back.DTO.DemandeResponse;
import com.example.gestion_conges_back.entity.DemandeConge;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.service.DemandeCongeService;

@RestController
@RequestMapping("/api/conges")
@CrossOrigin(origins = { "http://localhost:5173", "null" })
public class DemandeCongeController {

    private final DemandeCongeService demandeCongSer;
    private final Employerepository emprep;

    public DemandeCongeController(DemandeCongeService demandeCongSer, Employerepository emprep) {
        this.demandeCongSer = demandeCongSer;
        this.emprep = emprep;
    }

    private Employe employeConnecte() {
        return (Employe) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping("/creer")
    public String creer(@RequestBody DemandeCongeRequest request) {
        Long idE = employeConnecte().getIdE();
 
        return demandeCongSer.creerDemandeConge(request, idE);
    }

    @GetMapping("/all")
    public List<DemandeConge> getAll() {

        Employe employe = employeConnecte();

        if (employe.getRole() != RoleEnum.RH && employe.getRole() != RoleEnum.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Seuls le RH et les managers peuvent consulter toutes les demandes");
        }

        return demandeCongSer.getAllDemandeConge();
    }

    @GetMapping("/{employeId}")
    public List<DemandeResponse> getCongeIdE(@PathVariable Long employeId) {

        Employe employe = employeConnecte();

        boolean estRhOuManager = employe.getRole() == RoleEnum.RH || employe.getRole() == RoleEnum.MANAGER;
        boolean regardeSesPropresDonnees = employe.getIdE().equals(employeId);

        if (!estRhOuManager && !regardeSesPropresDonnees) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Vous ne pouvez consulter que vos propres demandes");
        }

        return demandeCongSer.getAllDemandeCongeIdE(employeId);
    }

    @PutMapping("/annuler/{id}")
    public String annuler(@PathVariable Long id) {
        Employe employe = employeConnecte();
        DemandeConge demande = demandeCongSer.getDemandeParId(id);

        
        boolean estProprietaire = demande.getEmploye().getIdE().equals(employe.getIdE());

        if (!estProprietaire) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Vous ne pouvez annuler que vos propres demandes");
        }
        return demandeCongSer.annulerDemandeConge(id);
    }

    @GetMapping("/manager/{managerId}")
    public List<DemandeManResponse> getCongeParManager(@PathVariable Long managerId) {
        Employe employe = employeConnecte();

        boolean estManager =employe.getRole() == RoleEnum.MANAGER;
        if (!estManager) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "pour manager seulment");
        }
        return demandeCongSer.getDemandesParManagerCong(managerId);
    }
}
