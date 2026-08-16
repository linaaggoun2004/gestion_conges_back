package com.example.gestion_conges_back.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Notification;

@Repository
public interface Notificationrepository extends ListCrudRepository<Notification, Long> {

    List<Notification> findByEmployeIdEOrderByDateEnvoiDesc(Long employeId);
    long countByEmployeIdEAndLuFalse(Long employeId);
}
