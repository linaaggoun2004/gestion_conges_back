package com.example.gestion_conges_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.gestion_conges_back.DTO.ChangerRoleRequest;
import com.example.gestion_conges_back.DTO.EmployeListDto;
import com.example.gestion_conges_back.DTO.EquipeMembreDto;
import com.example.gestion_conges_back.DTO.IndicateurResponse;
import com.example.gestion_conges_back.DTO.ManagerRequest;
import com.example.gestion_conges_back.DTO.ProfilResponse;
import com.example.gestion_conges_back.DTO.ReinitialisatioRequest;
import com.example.gestion_conges_back.DTO.RolseResponse;
import com.example.gestion_conges_back.DTO.SoldeUpdateRequest;
import com.example.gestion_conges_back.DTO.Soldedto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.entity.SoldeConge;
import com.example.gestion_conges_back.service.EmpService;
import com.example.gestion_conges_back.service.EquipeService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173", "null", "http://localhost:9090" })
@RestController
@RequestMapping("/api/emp")
@RequiredArgsConstructor
public class EmpController {

    private final EmpService empServ;

    private final EquipeService equipeService;
    private Employe employeConnecte() {
        return (Employe) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PutMapping("/role/{id}")
    public RolseResponse changerRole(@PathVariable Long id,
            @RequestBody ChangerRoleRequest request) {

        if (employeConnecte().getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut créer un service");
        }
        return empServ.changerRole(id, request.getRole());
    }

    @PutMapping("/manager/{id}")
    @PreAuthorize("hasRole('RH')")
    public Employe changerManager(
            @PathVariable Long id,
            @RequestBody ManagerRequest request) {

        return empServ.changerManager(id, request.getManagerId());
    }

    @GetMapping("/{id}")
    public ProfilResponse getEmp(@PathVariable Long id){
        return empServ.getEmploye2(id);
    }
    @GetMapping("/indi")
    @PreAuthorize("hasRole('RH')")
    public IndicateurResponse getTotal(){
        return empServ.indicateurs();
    }

    @GetMapping("/indiMan/{id}")
    public IndicateurResponse getTotalManager(@PathVariable Long id){
        return empServ.indicateursManager(id);
    }


    @GetMapping("/all")
    public List<EmployeListDto> getAllEmployes() {
        Employe connecte = employeConnecte();
        if (connecte.getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut consulter la liste des employés");
        }
        return empServ.gatAllEmployesAvecDetails();
    }

    @PutMapping("/solde/{id}")
    public SoldeConge modifierSolde(@PathVariable Long id, @RequestBody SoldeUpdateRequest request) {
        Employe connecte = employeConnecte();
        if (connecte.getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut modifier le solde");
        }
        return empServ.modifierSolde(id, request.getTotalJours());
    }

    @PostMapping("/soldes/reinitialiser")
    public void reinitialiserSoldes(@RequestBody ReinitialisatioRequest request) {
        Employe connecte = employeConnecte();
        if (connecte.getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut réinitialiser les soldes");
        }
        empServ.reinitialiserTousLesSoldes(request.getNouvelleValeur());
    }

    @GetMapping("solde")
    public Soldedto getSolde() {
        Employe connecte = employeConnecte();
        return empServ.getSolde(connecte.getIdE());
    }

    @GetMapping("/equipe")
public List<EquipeMembreDto> getMonEquipe() {
    Employe connecte = employeConnecte();
    if (connecte.getRole() != RoleEnum.MANAGER) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un manager peut consulter son équipe");
    }
    return equipeService.getEquipeDuManager(connecte.getIdE());
}




        
    
}
