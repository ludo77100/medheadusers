package com.medhead.usersmicroservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * Ce service est responsable de la génération, vérification et validation des tokens JWT.
 *
 * Principales fonctionnalités :
 * - Générer des tokens JWT
 * - Extraire des informations d'un token JWT
 * - Vérifier la validité d'un token JWT
 *
 */
@Service
public class JwtService {

    /**
     * Clé secrète utilisée pour signer les tokens JWT
     *
     * Elle est injectée depuis la configuration application.properties
     */
    @Value("${security.jwt.secret-key}")
    public String secretKey;

    /**
     * Durée d'expiration du token JWT
     *
     * Elle est injectée depuis la configuration application.properties
     */
    @Value("${security.jwt.expiration-time}")
    public long jwtExpiration;

    /**
     * Extraire le nom d'utilisateur du token JWT
     *
     * @param token Token JWT à analyser.
     * @return Nom d'utilisateur extrait du token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return generateToken(extraClaims, userDetails);
    }

    /**
     * Générer un token JWT à partir des détails de l'utilisateur
     *
     * @param userDetails Détails de l'utilisateur.
     * @return Token JWT généré.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construire le token JWT
     *
     * @param userDetails Détails de l'utilisateur
     * @param expiration Durée de validité du token
     *
     * @return Token JWT généré.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifier si le token JWT est valide
     *
     * @param token Token JWT à vérifier
     * @param userDetails Détails de l'utilisateur
     *
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    /**
     * Vérifier si le token JWT est expiré
     *
     * @param token Token JWT à vérifier
     *
     * @return true si le token est expiré, false sinon
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    /**
     * Extraire la date d'expiration du token JWT
     *
     * @param token Token JWT à analyser
     *
     * @return Date d'expiration du token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}