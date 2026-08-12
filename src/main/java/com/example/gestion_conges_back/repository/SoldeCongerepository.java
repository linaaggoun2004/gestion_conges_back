package com.example.gestion_conges_back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.SoldeConge;

@Repository
public interface SoldeCongerepository extends ListCrudRepository<SoldeConge, Long> {

    Optional<SoldeConge> findByEmployeIdEAndAnnee(Long employeId, Integer annee);
    List<SoldeConge> findByAnnee(Integer annee);
}