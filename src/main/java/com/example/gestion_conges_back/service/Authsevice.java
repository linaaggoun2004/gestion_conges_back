package com.example.gestion_conges_back.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestion_conges_back.DTO.AuthResponse;
import com.example.gestion_conges_back.DTO.LoginRequest;
import com.example.gestion_conges_back.DTO.SignupRequest;
import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.entity.RoleEnum;
import com.example.gestion_conges_back.entity.Service;
import com.example.gestion_conges_back.filter.JwtUtil;
import com.example.gestion_conges_back.repository.Employerepository;
import com.example.gestion_conges_back.repository.Servicerepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Authsevice {
    private final Employerepository employeRep;
    private final PasswordEncoder passwordEncoder;
    private final Servicerepository serviceRep;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (employeRep.existsByEmail(request.getEmail())) {
            System.out.println("vous avez deja un compte ");
            return null;
        }
        Employe employe = new Employe();
        employe.setNom(request.getNom());
        employe.setPrenom(request.getPrenom());
        employe.setDateNaissance(request.getDateNaissance().toString());
        employe.setEmail(request.getEmail());
        employe.setPoste(request.getPoste());
        employe.setDateEntree(request.getDateEntree());
        employe.setTelephone(request.getTelephone());
        employe.setAdresse(request.getAdresse());
        employe.setSituationFamiliale(request.getSituationFamiliale());

        employe.setMdp(passwordEncoder.encode(request.getMdp()));
        employe.setRole(RoleEnum.EMPLOYE);

        if (request.getServiceId() != null) {

            Optional<Service> serviceOptional = serviceRep.findById(request.getServiceId());

            if (serviceOptional.isPresent()) {

                Service service = serviceOptional.get();
                employe.setService(service);
                Employe manager = service.getManager();
                if (manager == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Ce service n'a pas encore de manager."
                    );
                }

                employe.setManager(manager);

            } else {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Service introuvable avec l'id : " + request.getServiceId());
            }
        }
        Employe e = employeRep.save(employe);
        String token = jwtUtil.generateToken(e);
        return new AuthResponse(
                token,
                e.getIdE(),
                e.getNom(),
                e.getPrenom(),
                e.getUsername(),
                e.getRole().name());

    }

    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMdp()));
        } catch (Exception e) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        Employe emp = employeRep.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Employé introuvable"));

        String token = jwtUtil.generateToken(emp);

        return new AuthResponse(
                token,
                emp.getIdE(),
                emp.getNom(),
                emp.getPrenom(),
                emp.getUsername(),
                emp.getRole().name());

    }
}