package com.example.gestion_conges_back.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_conges_back.DTO.NotificationDto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.service.NotificationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173", "null", "http://localhost:9090" })
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    private Employe employeConnecte() {
        return (Employe) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public List<NotificationDto> getMesNotifications() {
        return notificationService.getNotificationsPour(employeConnecte().getIdE());
    }

    @GetMapping("/non-lues")
    public Map<String, Long> compterNonLues() {
        return Map.of("count", notificationService.compterNonLues(employeConnecte().getIdE()));
    }

    @PutMapping("/{id}/lu")
    public void marquerLu(@PathVariable Long id) {
        notificationService.marquerLu(id, employeConnecte().getIdE());
    }

    @PutMapping("/lu-tout")
    public void marquerToutLu() {
        notificationService.marquerToutLu(employeConnecte().getIdE());
    }
}
