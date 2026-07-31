package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.SoldeConge;

@Repository
public interface SoldeCongerepository extends ListCrudRepository<SoldeConge, Long> {

    // List<SoldeConge> findByEmployeIdE(Long employeId);

    // Optional<SoldeConge> findByEmployeIdEAndAnnee(Long employeId, Integer annee);
}