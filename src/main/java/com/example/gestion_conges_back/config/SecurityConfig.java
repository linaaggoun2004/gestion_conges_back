package com.example.gestion_conges_back.config;

import com.example.gestion_conges_back.filter.Jwtfilter;
import com.example.gestion_conges_back.filter.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration centrale de la securite de l'application.
 *
 * Repond a 3 questions :
 * 1. Comment verifie-t-on un mot de passe ? -> passwordEncoder()
 * 2. Comment retrouve-t-on un utilisateur au login ? ->
 * authenticationProvider()
 * 3. Quelles routes sont publiques / protegees, et par quel filtre ? ->
 * securityFilterChain()
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // active @PreAuthorize("hasRole('RH')") etc. sur les controllers/services
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final Jwtfilter jwtAuthenticationFilter;

    /**
     * Encode les mots de passe avec BCrypt (jamais de mot de passe en clair en
     * base).
     * Utilise a la fois pour :
     * - hasher le mot de passe lors du signup
     * - verifier le mot de passe lors du login
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Relie notre UserDetailsService (charge un Employe par email)
     * avec notre PasswordEncoder (verifie le mot de passe hashe).
     * Utilise uniquement au moment du LOGIN.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Necessaire pour pouvoir appeler authenticationManager.authenticate(...)
     * dans AuthService.login(...).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Autorise le frontend (ex: React sur localhost:3000) a appeler cette API.
     * A adapter avec l'URL reelle de votre frontend en production.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Definit les regles d'acces : quelles routes sont publiques,
     * lesquelles necessitent d'etre authentifie, et branche le filtre JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Pas besoin de CSRF : on utilise des tokens JWT, pas de sessions/cookies
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        // Routes publiques : inscription et connexion
                        .requestMatchers("/api/auth/**").permitAll()
                        // Tout le reste necessite d'etre authentifie
                        .anyRequest().authenticated())

                // Pas de session HTTP classique : chaque requete doit porter son propre token
                // JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider())

                // Notre filtre JWT s'execute avant le filtre standard de Spring Security
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}