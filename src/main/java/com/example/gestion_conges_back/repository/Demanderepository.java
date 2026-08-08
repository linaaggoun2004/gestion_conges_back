package com.example.gestion_conges_back.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Demande;
import com.example.gestion_conges_back.entity.StatutEnum;

@Repository
public interface Demanderepository extends ListCrudRepository<Demande, Long> {

    List<Demande> findByEmployeIdE(Long employeId);

    Long countByStatut(StatutEnum statut);

    // List<Demande> findByEmployeIdEAndStatut(Long employeId, StatutEnum statut);
}
