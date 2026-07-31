package com.example.gestion_conges_back.filter;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gestion_conges_back.entity.Employe;
import com.example.gestion_conges_back.repository.Employerepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Employerepository employeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
         * return employeRepository.findByEmail(email)
         * .orElseThrow(() -> new UsernameNotFoundException(
         * "Aucun employe trouve avec l'email : " + email));
         */

        Optional<Employe> employe = employeRepository.findByEmail(email);
        if (employe.isPresent()) {
            return employe.get();
        }
        throw new UsernameNotFoundException(
                "Aucun employe trouve avec l'email : " + email);
    }
}
