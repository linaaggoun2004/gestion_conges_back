package com.example.gestion_conges_back.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_conges_back.entity.Service;

@Repository
public interface Servicerepository extends ListCrudRepository<Service, Long> {

}
