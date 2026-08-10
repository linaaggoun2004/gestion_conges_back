package com.example.gestion_conges_back.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Jwtfilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        // IMPORTANT : tout le traitement du token (extraction ET chargement
        // de l'utilisateur) est maintenant dans UN SEUL bloc try/catch.
        // Avant, seule l'extraction etait protegee : si l'email extrait
        // ne correspondait a AUCUN utilisateur en base (compte supprime,
        // token perime d'un ancien test...), loadUserByUsername() levait
        // une UsernameNotFoundException non interceptee, ce qui remontait
        // jusqu'a Spring Security et provoquait un 403 meme sur une route
        // publique (permitAll), car l'erreur survient AVANT que la regle
        // d'autorisation soit evaluee.
        try {
            final String email = jwtUtil.extractEmail(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token invalide, expire, signature incorrecte, ou utilisateur
            // introuvable -> on ignore simplement l'authentification.
            // Si la route necessite d'etre connecte, elle renverra 401/403
            // plus loin via les regles authorizeHttpRequests (comportement normal).
            // Si la route est publique (permitAll), la requete continue sans probleme.
            System.out.println("JWT rejeté : " + e.getClass().getSimpleName() + " -> " + e.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}