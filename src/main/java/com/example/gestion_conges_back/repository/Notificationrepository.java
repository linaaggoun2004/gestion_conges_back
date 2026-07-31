package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Notification;

@Repository
public interface Notificationrepository extends ListCrudRepository<Notification, Long> {

    // List<Notification> findByEmployeIdEOrderByDateEnvoiDesc(Long employeId);

    // List<Notification> findByEmployeIdEAndLu(Long employeId, Boolean lu);
}
