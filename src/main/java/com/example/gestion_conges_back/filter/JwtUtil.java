package com.example.gestion_conges_back.filter;

import java.util.Date;

import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
    public static final String SECRET = "aVeryLongAndComplexSecretKeyForYourJWTApplicationThatIsAtLeast256BitsLong";

    /*
     * public String generateToken(String email) {
     * Map<String, Object> claims = new HashMap<>();
     * return createToken(claims, email);
     * }
     * 
     * public String createToken(Map<String, Object> claims, String id_u) {
     * return Jwts.builder()
     * .setClaims(claims)
     * .setSubject(id_u)
     * .setIssuedAt(new Date())
     * .setExpiration(new Date(System.currentTimeMillis() + 3600000))
     * .signWith(getSignKey(), SignatureAlgorithm.HS256)
     * .compact();
     * }
     * 
     * private Key getSignKey() {
     * byte[] keyBytes = Decoders.BASE64.decode(SECRET);
     * return Keys.hmacShaKeyFor(keyBytes);
     * }
     */

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genere un token JWT pour un utilisateur qui vient de se connecter avec
     * succes.
     * Le "subject" du token est son email (= username chez nous).
     */
    public String generateToken(UserDetails userDetails) {
        Date maintenant = new Date();
        Date expiration = new Date(maintenant.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(maintenant)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public String getSubjectFromToken(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }
}
