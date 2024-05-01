package com.p3_oc_fs.chatop.dtos;

/**
 * DTO (Data Transfer Object) représentant les informations de connexion d'un utilisateur.
 */
public class LoginUserDto {
    private String email;
    private String password;

    /**
     * Renvoie l'email de l'utilisateur.
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'utilisateur.
     * @param email L'email à définir.
     * @return L'objet LoginUserDto mis à jour.
     */
    public LoginUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Renvoie le mot de passe de l'utilisateur.
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * @param password Le mot de passe à définir.
     * @return L'objet LoginUserDto mis à jour.
     */
    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Renvoie une représentation textuelle de l'objet LoginUserDto.
     * @return Une chaîne représentant l'objet LoginUserDto.
     */
    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

