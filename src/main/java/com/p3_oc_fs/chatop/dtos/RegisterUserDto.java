package com.p3_oc_fs.chatop.dtos;

/**
 * DTO (Data Transfer Object) représentant les informations d'inscription d'un nouvel utilisateur.
 */
public class RegisterUserDto {
    private String email;
    private String password;
    private String name;

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
     * @return L'objet RegisterUserDto mis à jour.
     */
    public RegisterUserDto setEmail(String email) {
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
     * @return L'objet RegisterUserDto mis à jour.
     */
    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Renvoie le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param name Le nom à définir.
     * @return L'objet RegisterUserDto mis à jour.
     */
    public RegisterUserDto name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Renvoie une représentation textuelle de l'objet RegisterUserDto.
     * @return Une chaîne représentant l'objet RegisterUserDto.
     */
    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

