package com.p3_oc_fs.chatop.dtos;

import java.util.Date;

/**
 * DTO (Data Transfer Object) représentant les informations d'un utilisateur.
 * Cette classe est utilisée pour renvoyer les détails d'un utilisateur authentifié.
 */
public class UserGetMe {
    private Integer id;
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;

    /**
     * Constructeur de la classe UserGetMe.
     * @param id L'identifiant de l'utilisateur.
     * @param name Le nom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param created_at La date de création de l'utilisateur.
     * @param updated_at La date de mise à jour de l'utilisateur.
     */
    public UserGetMe(Integer id, String name, String email, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    /**
     * Renvoie l'identifiant de l'utilisateur.
     * @return L'identifiant de l'utilisateur.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renvoie le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie l'email de l'utilisateur.
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Renvoie la date de création de l'utilisateur.
     * @return La date de création de l'utilisateur.
     */
    public Date getcreated_at() {
        return created_at;
    }

    /**
     * Renvoie la date de mise à jour de l'utilisateur.
     * @return La date de mise à jour de l'utilisateur.
     */
    public Date getupdated_at() {
        return updated_at;
    }
}

