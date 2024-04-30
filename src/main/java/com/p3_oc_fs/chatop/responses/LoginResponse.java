package com.p3_oc_fs.chatop.responses;

/**
 * Réponse renvoyée après une demande d'authentification.
 */
public class LoginResponse {
    private String token;
    private long expiresIn;

    /**
     * Renvoie le token JWT.
     * @return Le token JWT.
     */
    public String getToken() {
        return token;
    }

    /**
     * Définit le token JWT.
     * @param token Le token JWT à définir.
     * @return L'objet LoginResponse mis à jour.
     */
    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Renvoie la durée de validité du token en millisecondes.
     * @return La durée de validité du token en millisecondes.
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Définit la durée de validité du token en millisecondes.
     * @param expiresIn La durée de validité du token à définir en millisecondes.
     * @return L'objet LoginResponse mis à jour.
     */
    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    /**
     * Renvoie une représentation textuelle de l'objet LoginResponse.
     * @return Une chaîne représentant l'objet LoginResponse.
     */
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}

