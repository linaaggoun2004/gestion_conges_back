package com.example.gestion_conges_back.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Demande;
import com.example.gestion_conges_back.entity.DemandeConge;

@Repository
public interface DemandeCongerepository extends ListCrudRepository<DemandeConge, Long> {

    List<DemandeConge> findByEmployeIdE(Long employeId);
}