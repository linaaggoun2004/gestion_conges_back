package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Document;

@Repository
public interface Documentrepository extends ListCrudRepository<Document, Long> {
    // List<Document> findByDemandeArretMaladieIdD(Long demandeId);
}
