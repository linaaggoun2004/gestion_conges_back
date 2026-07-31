package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.DemandeArretMaladie;

@Repository
public interface DemandeArretMaladierepository extends ListCrudRepository<DemandeArretMaladie, Long> {

}