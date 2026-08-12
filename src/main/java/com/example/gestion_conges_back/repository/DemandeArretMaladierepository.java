package com.example.gestion_conges_back.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.DemandeArretMaladie;
import com.example.gestion_conges_back.entity.StatutEnum;

@Repository
public interface DemandeArretMaladierepository extends ListCrudRepository<DemandeArretMaladie, Long> {
    List<DemandeArretMaladie> findByEmploye_IdE(Long employeId);

    List<DemandeArretMaladie> findByStatut(StatutEnum statut);
}