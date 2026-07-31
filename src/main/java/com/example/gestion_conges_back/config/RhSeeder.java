package com.example.gestion_conges_back.config;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.repository.Employerepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RhSeeder implements CommandLineRunner {
    private final Employerepository employeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.rh.email}")
    private String seedEmail;

    @Value("${app.seed.rh.password}")
    private String seedPassword;

    @Value("${app.seed.rh.nom}")
    private String seedNom;

    @Value("${app.seed.rh.prenom}")
    private String seedPrenom;

    @Override
    public void run(String... args) {

        boolean unRhExisteDeja = employeRepository.existsByRole(RoleEnum.RH);

        if (unRhExisteDeja) {
            log.info("Un compte RH existe deja, aucun seed necessaire.");
            return;
        }

        Employe rh = new Employe();
        rh.setNom(seedNom);
        rh.setPrenom(seedPrenom);
        rh.setEmail(seedEmail);
        rh.setMdp(passwordEncoder.encode(seedPassword));
        rh.setPoste("Ressources Humaines");
        rh.setRole(RoleEnum.RH);
        rh.setDateEntree(LocalDate.now());

        employeRepository.save(rh);

        log.warn("========================================================");
        log.warn("Compte RH initial cree automatiquement :");
        log.warn("  Email    : {}", seedEmail);
        log.warn("  Mot de passe : celui defini dans app.seed.rh.password");
        log.warn("  -> Changez ce mot de passe des que possible !");
        log.warn("========================================================");
    }
}
