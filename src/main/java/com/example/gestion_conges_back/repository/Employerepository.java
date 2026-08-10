package com.example.gestion_conges_back.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;

@Repository
public interface Employerepository extends ListCrudRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRole(RoleEnum role);
    Long countByManager_IdE(Long id );
}
