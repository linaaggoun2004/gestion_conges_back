package com.example.gestion_conges_back.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.DemandeConge;
import com.example.gestion_conges_back.entity.StatutEnum;

@Repository
public interface DemandeCongerepository extends ListCrudRepository<DemandeConge, Long> {

    List<DemandeConge> findByEmploye_IdE(Long employeId);
    List<DemandeConge> findByStatut(StatutEnum statut);
}