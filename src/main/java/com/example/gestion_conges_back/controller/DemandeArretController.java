package com.example.gestion_conges_back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.DemandeArretRequest;
import com.example.gestion_conges_back.DTO.DemandeResponse;
import com.example.gestion_conges_back.entity.DemandeArretMaladie;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.service.DemandeArretMaladieService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/Arret")
@CrossOrigin(origins = { "http://localhost:5173", "null" })
public class DemandeArretController {
    private final DemandeArretMaladieService demandeArretSer;
    private final Employerepository emprep;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DemandeArretController(DemandeArretMaladieService demandeArretSer, Employerepository emprep) {
        this.demandeArretSer = demandeArretSer;
        this.emprep = emprep;
    }

    private Employe employeConnecte() {
        return (Employe) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping(value = "/creer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String creerArret(@RequestPart("request") String requestJson,
            @RequestPart("certificat") MultipartFile certificat) throws IOException {

        DemandeArretRequest request = objectMapper.readValue(requestJson, DemandeArretRequest.class);
        Long idE = employeConnecte().getIdE();

        return demandeArretSer.creerDemandeArret(request, certificat, idE);
    }

    @GetMapping("/all")
    public List<DemandeArretMaladie> getAllArret() {

        Employe employe = employeConnecte();

        if (employe.getRole() != RoleEnum.RH && employe.getRole() != RoleEnum.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Seuls le RH et les managers peuvent consulter toutes les demandes");
        }

        return demandeArretSer.getAllDemandeArretMal();
    }

    @GetMapping("/{employeId}")
    public List<DemandeResponse> getArretIdE(@PathVariable Long employeId) {

        Employe employe = employeConnecte();

        boolean estRhOuManager = employe.getRole() == RoleEnum.RH || employe.getRole() == RoleEnum.MANAGER;
        boolean regardeSesPropresDonnees = employe.getIdE().equals(employeId);

        if (!estRhOuManager && !regardeSesPropresDonnees) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Vous ne pouvez consulter que vos propres demandes");
        }

        return demandeArretSer.getAllDemandeArretIdE(employeId);
    }

    @PutMapping("/annuler/{id}")
    public String annuler(@PathVariable Long id) {
        Employe employe = employeConnecte();
        DemandeArretMaladie demande = demandeArretSer.getDemandeArretParId(id);

        boolean estProprietaire =
            demande.getEmploye().getIdE().equals(employe.getIdE());

        if (!estProprietaire) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Vous ne pouvez annuler que vos propres demandes");
        }
        return demandeArretSer.annulerDemandeArretMal(id);
    }
}
