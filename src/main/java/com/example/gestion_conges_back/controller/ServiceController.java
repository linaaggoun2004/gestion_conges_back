package com.example.gestion_conges_back.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_conges_back.DTO.Servicedto;
import com.example.gestion_conges_back.entity.Service;
import com.example.gestion_conges_back.repository.Servicerepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final Servicerepository servicerep;

    @GetMapping
    public ResponseEntity<List<Servicedto>> getAllServices() {

        List<Service> liste = this.servicerep.findAll();

        List<Servicedto> services = new ArrayList<>();

        for (Service s : liste) {
            Servicedto d = new Servicedto(s.getIdS(), s.getNom(), s.getDescription());
            services.add(d);
        }
        return ResponseEntity.ok(services);

    }
}
