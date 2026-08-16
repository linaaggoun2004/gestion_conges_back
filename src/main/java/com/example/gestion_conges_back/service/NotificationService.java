package com.example.gestion_conges_back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.NotificationDto;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.Notification;
import com.example.gestion_conges_back.repository.Notificationrepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Notificationrepository notificationRepository;

    public void creerNotification(Employe destinataire, String contenu) {
        if (destinataire == null) return; // pas de manager assigné -> pas de notification, pas d'erreur
        Notification n = new Notification();
        n.setEmploye(destinataire);
        n.setContenu(contenu);
        notificationRepository.save(n);
    }

    public List<NotificationDto> getNotificationsPour(Long employeId) {
        return notificationRepository.findByEmployeIdEOrderByDateEnvoiDesc(employeId).stream()
                .map(n -> new NotificationDto(n.getIdN(), n.getContenu(), n.getDateEnvoi(), n.getLu()))
                .collect(Collectors.toList());
    }

    public long compterNonLues(Long employeId) {
        return notificationRepository.countByEmployeIdEAndLuFalse(employeId);
    }

    public void marquerLu(Long notifId, Long employeId) {
        Notification n = notificationRepository.findById(notifId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification introuvable"));
        if (!n.getEmploye().getIdE().equals(employeId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette notification ne vous appartient pas");
        }
        n.setLu(true);
        notificationRepository.save(n);
    }

    public void marquerToutLu(Long employeId) {
        List<Notification> notifs = notificationRepository.findByEmployeIdEOrderByDateEnvoiDesc(employeId);
        notifs.forEach(n -> n.setLu(true));
        notificationRepository.saveAll(notifs);
    }
}
