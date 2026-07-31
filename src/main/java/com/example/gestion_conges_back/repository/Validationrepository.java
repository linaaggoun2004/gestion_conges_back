package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Validation;

@Repository
public interface Validationrepository extends ListCrudRepository<Validation, Long> {

    // List<Validation> findByDemandeIdD(Long demandeId);

    // List<Validation> findByValidateurIdE(Long validateurId);
}
