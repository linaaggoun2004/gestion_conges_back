package com.example.gestion_conges_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.AssignerManagerRequest;
import com.example.gestion_conges_back.DTO.ServiceCreateRequest;
import com.example.gestion_conges_back.DTO.Servicedto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.repository.Servicerepository;
import com.example.gestion_conges_back.service.ServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final Servicerepository servicerep;
    private final ServiceService serviceService;

    private Employe employeConnecte() {
        return (Employe) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<Servicedto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServiceAvecStats());
    }

    @PostMapping("/creer")
    public com.example.gestion_conges_back.entity.Service creer(@RequestBody ServiceCreateRequest request) {
        if (employeConnecte().getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut créer un service");
        }
        return serviceService.creerService(request);
    }

    @PutMapping("/{id}/manager")
    public com.example.gestion_conges_back.entity.Service assignerManager(
            @PathVariable Long id, @RequestBody AssignerManagerRequest request) {
        if (employeConnecte().getRole() != RoleEnum.RH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul le RH peut assigner un manager");
        }
        return serviceService.assignerManager(id, request.getManagerId());
    }
}
