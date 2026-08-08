package com.example.gestion_conges_back.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_conges_back.DTO.ChangerRoleRequest;
import com.example.gestion_conges_back.DTO.IndicateurResponse;
import com.example.gestion_conges_back.DTO.ManagerRequest;
import com.example.gestion_conges_back.DTO.ProfilResponse;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.service.EmpService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173", "null", "http://localhost:9090" })
@RestController
@RequestMapping("/api/emp")
@RequiredArgsConstructor
public class EmpController {

    private final EmpService empServ;

    @PutMapping("/role/{id}")
    @PreAuthorize("hasRole('RH')")
    public Employe changerRole(@PathVariable Long id,
            @RequestBody ChangerRoleRequest request) {

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


        
    
}
