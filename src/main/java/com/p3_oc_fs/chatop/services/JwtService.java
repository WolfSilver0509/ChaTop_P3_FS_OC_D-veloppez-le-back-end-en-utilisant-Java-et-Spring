package com.p3_oc_fs.chatop.services;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service pour la gestion des tokens JWT.
 */
@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extrait le nom d'utilisateur du token JWT.
     * @param token Le token JWT à partir duquel extraire le nom d'utilisateur.
     * @return Le nom d'utilisateur extrait du token JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une revendication spécifique du token JWT en utilisant un résolveur de revendications.
     * @param token Le token JWT à partir duquel extraire la revendication.
     * @param claimsResolver Le résolveur de revendications pour extraire la revendication souhaitée.
     * @param <T> Le type de la revendication à extraire.
     * @return La revendication extraite du token JWT.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génère un token JWT pour un utilisateur donné.
     * @param userDetails Les détails de l'utilisateur pour lesquels générer le token JWT.
     * @return Le token JWT généré.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Génère un token JWT avec des revendications supplémentaires pour un utilisateur donné.
     * @param extraClaims Les revendications supplémentaires à inclure dans le token JWT.
     * @param userDetails Les détails de l'utilisateur pour lesquels générer le token JWT.
     * @return Le token JWT généré.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Renvoie le temps d'expiration des tokens JWT.
     * @return Le temps d'expiration des tokens JWT.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construit un token JWT avec les revendications et le temps d'expiration spécifiés.
     * @param extraClaims Les revendications supplémentaires à inclure dans le token JWT.
     * @param userDetails Les détails de l'utilisateur pour lesquels générer le token JWT.
     * @param expiration Le temps d'expiration du token JWT.
     * @return Le token JWT construit.
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
     * Vérifie si un token JWT est valide pour un utilisateur donné.
     * @param token Le token JWT à valider.
     * @param userDetails Les détails de l'utilisateur pour lesquels valider le token JWT.
     * @return true si le token JWT est valide, sinon false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Vérifie si un token JWT a expiré.
     * @param token Le token JWT à vérifier.
     * @return true si le token JWT a expiré, sinon false.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d'expiration d'un token JWT.
     * @param token Le token JWT à partir duquel extraire la date d'expiration.
     * @return La date d'expiration extraite du token JWT.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait toutes les revendications d'un token JWT.
     * @param token Le token JWT à partir duquel extraire toutes les revendications.
     * @return Toutes les revendications extraites du token JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Récupère la clé de signature à partir de la clé secrète spécifiée.
     * @return La clé de signature extraite de la clé secrète.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
